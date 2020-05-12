package com.hailian.ylwmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.gson.Gson;
import com.hailian.ylwmall.common.B2BMallException;
import com.hailian.ylwmall.common.Constants;
import com.hailian.ylwmall.common.OrderStatusEnum;
import com.hailian.ylwmall.common.pay.*;
import com.hailian.ylwmall.controller.vo.NewBeeMallUserVO;
import com.hailian.ylwmall.controller.vo.OrderGoodInfoVo;
import com.hailian.ylwmall.dto.pay.EnsureTradeBean;
import com.hailian.ylwmall.dto.pay.EnsureTradeCallBackDto;
import com.hailian.ylwmall.dto.pay.EnsureTradeReq;
import com.hailian.ylwmall.entity.StockNumDTO;
import com.hailian.ylwmall.entity.TbOrderPay;
import com.hailian.ylwmall.entity.TbPayRefund;
import com.hailian.ylwmall.entity.TbUserPay;
import com.hailian.ylwmall.entity.order.OrderInfo;
import com.hailian.ylwmall.exception.BusinessException;
import com.hailian.ylwmall.service.PayService;
import com.hailian.ylwmall.util.*;
import com.kjtpay.gateway.common.domain.VerifyResult;
import com.kjtpay.gateway.common.domain.base.RequestBase;
import com.kjtpay.gateway.common.domain.base.ResponseParameter;
import com.kjtpay.gateway.common.domain.instanttrade.TradeInfo;
import com.kjtpay.gateway.common.domain.traderefund.TradeRefundReq;
import com.kjtpay.gateway.common.enums.ReturnInfoEnum;
import com.kjtpay.gateway.common.enums.Terminal;
import com.kjtpay.gateway.common.util.security.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 支付服务类
 * @author 19033323
 */
@Slf4j
@Service
public class PayServiceImpl extends PayServiceBase implements PayService {

    /**
     * 担保支付
     */
    @Override
    @Transactional
    public RequestBase ensureTrade(EnsureTradeReq reqBean, HttpServletRequest request){
        // 生成支付订单号
        String ip = GetCilentIP.getIpAddr(request);
        NewBeeMallUserVO user = (NewBeeMallUserVO) request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY);
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(Long.parseLong(reqBean.getOrderId()));
        List<OrderGoodInfoVo> orderGoodInfoVos = orderGoodInfoMapper.selectByOrderId(orderInfo.getId());
        // 有一种方式支付成功就不再执行
        List<TbOrderPay> paySuccessList = orderPayDao.selectList(new QueryWrapper<TbOrderPay>()
                .eq("order_id", reqBean.getOrderId())
                .eq("is_deleted", "0")
                .gt("pay_status", PayStatusEnum.PAY_WAIT.getPayStatus()));
        if(!paySuccessList.isEmpty()){
            throw new BusinessException("已支付过，请不要重复提交");
        }

        TbOrderPay orderPay = orderPayDao.selectOne(new QueryWrapper<TbOrderPay>()
                .eq("order_id", reqBean.getOrderId())
                .eq("is_deleted", "0")
                .eq("pay_type", reqBean.getPayType()));
        if(orderPay == null){
            // 新增支付记录
            String outTradeNo = GetCodeUtil.getOrderId(user.getUserId());
            // 保存支付表
            orderPay = new TbOrderPay();
            orderPay.setPayType(Integer.parseInt(reqBean.getPayType()));
            orderPay.setOrderId(orderInfo.getId());
            orderPay.setOutTradeNo(outTradeNo);
            orderPay.setPayStatus(PayStatusEnum.PAY_ING.getPayStatus());
            orderPay.setCreateTime(new Date());
            orderPay.setUpdateTime(new Date());
            orderPay.setPayMoney(orderInfo.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
            orderPayDao.insert(orderPay);
        }

        // 交易信息
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setOutTradeNo(orderPay.getOutTradeNo());//平台（商户）单号
        tradeInfo.setSubject(orderGoodInfoVos == null || orderGoodInfoVos.isEmpty() ? "未知商品名称": orderGoodInfoVos.get(0).getGoodName());//商品名称
        tradeInfo.setPrice(ArithmeticUtil.strRound(orderInfo.getRealPrice().toString(),2));//单价，精确到两位小数 5000.00
        tradeInfo.setEnsureAmount(ArithmeticUtil.strRound(orderInfo.getRealPrice().toString(),2));
        tradeInfo.setQuantity("1");//数量
        tradeInfo.setTotalAmount(ArithmeticUtil.strRound(orderInfo.getRealPrice().toString(),2));//交易金额
        tradeInfo.setPayeeIdentityType("1");
        tradeInfo.setPayeeIdentity(kjtConfig.getPayeeidentity());//卖家会员id或登录账号
        tradeInfo.setNotifyUrl(kjtConfig.getEnsureTradeAsyncNotify());//服务器异步通知地址

        // 业务信息
        EnsureTradeBean tradeBizContent = new EnsureTradeBean();
        tradeBizContent.setPayerIdentityType("1");
        tradeBizContent.setPayerPlatformType("1");
        tradeBizContent.setPayerIdentity("anonymous");
        tradeBizContent.setPayerIp(ip);
        tradeBizContent.setBizProductCode(ProductCodeEnum.ENSURE_TRADE_20702.getCode());
        tradeBizContent.setCashierType("WEB");
        tradeBizContent.setTimeoutExpress("2h");//订单付款码2h有效
        tradeBizContent.setTradeInfo(tradeInfo);
        // 支付方式设置
        tradeBizContent = setPay(tradeBizContent, reqBean, user.getUserId(), orderInfo.getRealPrice());
        // 终端信息设置
        Map<String,String> terminalInfo = new HashMap<>();
        terminalInfo.put("terminal_type", Terminal.computer.getCode());//电脑
        terminalInfo.put("ip",ip);
        tradeBizContent.setTerminalInfo(terminalInfo);
        tradeBizContent.setReturnUrl(kjtConfig.getEnsureTradeReturnUrl()); // 同步返回地址

        return genRequestBase(gson.toJson(tradeBizContent), orderPay.getOutTradeNo(), KJTConstants.SERVICE_ENSURE_TRADE);
    }

    /**
     * 聚合钱包支付
     */
    @Override
    @Transactional
    public Result ensureTradePurse(EnsureTradeReq reqBean, HttpServletRequest request){
        // 生成支付订单号
        String ip = GetCilentIP.getIpAddr(request);
        NewBeeMallUserVO user = (NewBeeMallUserVO) request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY);
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(Long.parseLong(reqBean.getOrderId()));
        List<OrderGoodInfoVo> orderGoodInfoVos = orderGoodInfoMapper.selectByOrderId(orderInfo.getId());
        // 有一种方式支付成功就不再执行
        List<TbOrderPay> paySuccessList = orderPayDao.selectList(new QueryWrapper<TbOrderPay>()
                        .eq("order_id", reqBean.getOrderId())
                        .eq("is_deleted", "0")
                        .gt("pay_status", PayStatusEnum.PAY_WAIT.getPayStatus()));
        if(!paySuccessList.isEmpty()){
            return ResultGenerator.genFailResult("已支付过，请不要重复提交");
        }

        TbOrderPay orderPay = orderPayDao.selectOne(new QueryWrapper<TbOrderPay>()
                .eq("order_id", reqBean.getOrderId())
                .eq("is_deleted", "0")
                .eq("pay_type", reqBean.getPayType()));
        if(orderPay == null){
            // 新增支付记录
            String outTradeNo = GetCodeUtil.getOrderId(user.getUserId());
            // 保存支付表
            orderPay = new TbOrderPay();
            orderPay.setPayType(Integer.parseInt(reqBean.getPayType()));
            orderPay.setOrderId(orderInfo.getId());
            orderPay.setOutTradeNo(outTradeNo);
            orderPay.setPayStatus(PayStatusEnum.PAY_ING.getPayStatus());
            orderPay.setCreateTime(new Date());
            orderPay.setUpdateTime(new Date());
            orderPay.setPayMoney(orderInfo.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
            orderPayDao.insert(orderPay);
        }

        // 交易信息
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setOutTradeNo(orderPay.getOutTradeNo());//平台（商户）单号
        tradeInfo.setSubject(orderGoodInfoVos == null || orderGoodInfoVos.isEmpty() ? "未知商品名称": orderGoodInfoVos.get(0).getGoodName());//商品名称
        tradeInfo.setPrice(ArithmeticUtil.strRound(orderInfo.getRealPrice().toString(),2));//单价，精确到两位小数 5000.00
        tradeInfo.setEnsureAmount(ArithmeticUtil.strRound(orderInfo.getRealPrice().toString(),2));
        tradeInfo.setQuantity("1");//数量
        tradeInfo.setTotalAmount(ArithmeticUtil.strRound(orderInfo.getRealPrice().toString(),2));//交易金额
        tradeInfo.setPayeeIdentityType("1");
        tradeInfo.setPayeeIdentity(kjtConfig.getPayeeidentity());//卖家会员id或登录账号
        tradeInfo.setNotifyUrl(kjtConfig.getEnsureTradeAsyncNotify());//服务器异步通知地址

        // 业务信息
        EnsureTradeBean tradeBizContent = new EnsureTradeBean();
        tradeBizContent.setPayerIdentityType("1");
        tradeBizContent.setPayerPlatformType("1");
        tradeBizContent.setPayerIdentity("anonymous");
        tradeBizContent.setPayerIp(ip);
        tradeBizContent.setBizProductCode(ProductCodeEnum.ENSURE_TRADE_20702.getCode());
        tradeBizContent.setCashierType("WEB");
        tradeBizContent.setTimeoutExpress("2h");//订单付款码2h有效
        tradeBizContent.setTradeInfo(tradeInfo);
        // 支付方式设置
        tradeBizContent = setPay(tradeBizContent, reqBean, user.getUserId(), orderInfo.getRealPrice());
        // 终端信息设置
        Map<String,String> terminalInfo = new HashMap<>();
        terminalInfo.put("terminal_type", Terminal.computer.getCode());//电脑
        terminalInfo.put("ip",ip);
        tradeBizContent.setTerminalInfo(terminalInfo);
        tradeBizContent.setReturnUrl(kjtConfig.getEnsureTradeReturnUrl()); // 同步返回地址

        RequestBase requestBase = genRequestBase(gson.toJson(tradeBizContent), orderPay.getOutTradeNo(), KJTConstants.SERVICE_ENSURE_TRADE);
        ResponseParameter result = callKjt(requestBase);
        if(result == null){
            String failMsg = "聚合钱包支付失败，快捷通返回null";
            log.error(failMsg);
            orderPay.setFailMsg(failMsg);
            orderPay.setPayStatus(PayStatusEnum.PAY_FAIL.getPayStatus());
            orderPay.setUpdateTime(new Date());
            orderPayDao.updateById(orderPay);
            return ResultGenerator.genFailResult(failMsg);
        }else if(!ReturnInfoEnum.SUCCESS.getCode().equals(result.getCode())){
            // 支付失败
            String failMsg = StringUtils.isNotBlank(result.getSubMsg()) ? result.getSubMsg() : result.getMsg();
            log.error("聚合钱包支付失败: {}", failMsg);
            orderPay.setFailCode(result.getCode());
            orderPay.setFailMsg(result.getMsg());
            orderPay.setPayStatus(PayStatusEnum.PAY_FAIL.getPayStatus());
            orderPay.setUpdateTime(new Date());
            orderPayDao.updateById(orderPay);
            return ResultGenerator.genFailResult(failMsg);
        }else if(ReturnInfoEnum.SUCCESS.getCode().equals(result.getCode())){
            // 支付成功
            log.info("聚合钱包返回成功：{}", gson.toJson(result));
            JSONObject jsonObject = JSONObject.parseObject((String)result.getBizContent());

            orderPay.setPayStatus(PayStatusEnum.PAY_WAIT.getPayStatus());
            orderPay.setTradeNo(jsonObject.getString("trade_no"));
            orderPay.setUpdateTime(new Date());
            orderPayDao.updateById(orderPay);
            return ResultGenerator.genSuccessResult(jsonObject);
        }
        return ResultGenerator.genFailResult("支付未成功");
    }

    /**
     * 协议支付
     */
    @Override
    @Transactional
    public Result ensureTradeAgreement(EnsureTradeReq reqBean, HttpServletRequest request){
        // 生成支付订单号
        String ip = GetCilentIP.getIpAddr(request);
        NewBeeMallUserVO user = (NewBeeMallUserVO) request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY);
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(Long.parseLong(reqBean.getOrderId()));
        List<OrderGoodInfoVo> orderGoodInfoVos = orderGoodInfoMapper.selectByOrderId(orderInfo.getId());
        // 有一种方式支付成功就不再执行
        List<TbOrderPay> paySuccessList = orderPayDao.selectList(new QueryWrapper<TbOrderPay>()
                .eq("order_id", reqBean.getOrderId())
                .eq("is_deleted", "0")
                .gt("pay_status", PayStatusEnum.PAY_WAIT.getPayStatus()));
        if(!paySuccessList.isEmpty()){
            return ResultGenerator.genFailResult("已支付过，请不要重复提交");
        }

        TbOrderPay orderPay = orderPayDao.selectOne(new QueryWrapper<TbOrderPay>()
                .eq("order_id", reqBean.getOrderId())
                .eq("is_deleted", "0")
                .eq("pay_type", reqBean.getPayType()));
        if(orderPay == null){
            // 新增支付记录
            String outTradeNo = GetCodeUtil.getOrderId(user.getUserId());
            // 保存支付表
            orderPay = new TbOrderPay();
            orderPay.setPayType(Integer.parseInt(reqBean.getPayType()));
            orderPay.setOrderId(orderInfo.getId());
            orderPay.setOutTradeNo(outTradeNo);
            orderPay.setPayStatus(PayStatusEnum.PAY_ING.getPayStatus());
            orderPay.setCreateTime(new Date());
            orderPay.setUpdateTime(new Date());
            orderPay.setPayMoney(orderInfo.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
            orderPayDao.insert(orderPay);
        }

        // 交易信息
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setOutTradeNo(orderPay.getOutTradeNo());//平台（商户）单号
        tradeInfo.setSubject(orderGoodInfoVos == null || orderGoodInfoVos.isEmpty() ? "未知商品名称": orderGoodInfoVos.get(0).getGoodName());//商品名称
        tradeInfo.setPrice(ArithmeticUtil.strRound(orderInfo.getRealPrice().toString(),2));//单价，精确到两位小数 5000.00
        tradeInfo.setEnsureAmount(ArithmeticUtil.strRound(orderInfo.getRealPrice().toString(),2));
        tradeInfo.setQuantity("1");//数量
        tradeInfo.setTotalAmount(ArithmeticUtil.strRound(orderInfo.getRealPrice().toString(),2));//交易金额
        tradeInfo.setPayeeIdentityType("1");
        tradeInfo.setPayeeIdentity(kjtConfig.getPayeeidentity());//卖家会员id或登录账号
        tradeInfo.setNotifyUrl(kjtConfig.getEnsureTradeAsyncNotify());//服务器异步通知地址

        // 业务信息
        EnsureTradeBean tradeBizContent = new EnsureTradeBean();
        tradeBizContent.setPayerIdentityType("1");
        tradeBizContent.setPayerPlatformType("1");
        tradeBizContent.setPayerIdentity("anonymous");
        tradeBizContent.setPayerIp(ip);
        tradeBizContent.setBizProductCode(ProductCodeEnum.ENSURE_TRADE_20702.getCode());
        tradeBizContent.setCashierType("WEB");
        tradeBizContent.setTimeoutExpress("2h");//订单付款码2h有效
        tradeBizContent.setTradeInfo(tradeInfo);
        // 支付方式设置
        tradeBizContent = setPay(tradeBizContent, reqBean, user.getUserId(), orderInfo.getRealPrice());
        // 终端信息设置
        Map<String,String> terminalInfo = new HashMap<>();
        terminalInfo.put("terminal_type", Terminal.computer.getCode());//电脑
        terminalInfo.put("ip",ip);
        tradeBizContent.setTerminalInfo(terminalInfo);
        tradeBizContent.setReturnUrl(kjtConfig.getEnsureTradeReturnUrl()); // 同步返回地址

        RequestBase requestBase = genRequestBase(gson.toJson(tradeBizContent), orderPay.getOutTradeNo(), KJTConstants.SERVICE_ENSURE_TRADE);
        ResponseParameter result = callKjt(requestBase);
        if(result == null){
            String failMsg = "协议支付失败，快捷通返回null";
            log.error(failMsg);
            orderPay.setFailMsg(failMsg);
            orderPay.setPayStatus(PayStatusEnum.PAY_FAIL.getPayStatus());
            orderPay.setUpdateTime(new Date());
            orderPayDao.updateById(orderPay);
            return ResultGenerator.genFailResult(failMsg);
        }else if(!ReturnInfoEnum.SUCCESS.getCode().equals(result.getCode())){
            // 支付失败
            String failMsg = StringUtils.isNotBlank(result.getSubMsg()) ? result.getSubMsg() : result.getMsg();
            log.error("协议支付失败: {}", failMsg);
            orderPay.setFailCode(result.getCode());
            orderPay.setFailMsg(result.getMsg());
            orderPay.setPayStatus(PayStatusEnum.PAY_FAIL.getPayStatus());
            orderPay.setUpdateTime(new Date());
            orderPayDao.updateById(orderPay);
            return ResultGenerator.genFailResult(failMsg);
        }else if(ReturnInfoEnum.SUCCESS.getCode().equals(result.getCode())){
            // 支付成功
            log.info("协议支付成功：{}", gson.toJson(result));
            JSONObject jsonObject = JSONObject.parseObject((String)result.getBizContent());

            // 签约成功保存token_id
            TbUserPay userPay = userPayDao.selectOne(new QueryWrapper<TbUserPay>().eq("user_id", user.getUserId()));
            if(StringUtils.isBlank(userPay.getTokenId()) && StringUtils.isNotBlank(jsonObject.getString("token_id"))){
                userPay.setTokenId(jsonObject.getString("token_id"));
                userPay.setTokenIsvalid(0);
                userPay.setUpdateTime(new Date());
                userPayDao.updateById(userPay);
            }

            String smsNeed = jsonObject.getString("status");
            if("M".equalsIgnoreCase(smsNeed)){
                orderPay.setPayStatus(PayStatusEnum.PAY_WAIT.getPayStatus());
            }else if("S".equalsIgnoreCase(smsNeed)){
                orderPay.setPayStatus(PayStatusEnum.PAY_WAIT_CONFIRM.getPayStatus());
            }
            if(StringUtils.isNotBlank(jsonObject.getString("pay_token"))){
                orderPay.setTokenId(jsonObject.getString("token_id"));
                orderPay.setPayToken(jsonObject.getString("pay_token"));
            }
            orderPay.setTradeNo(jsonObject.getString("trade_no"));
            orderPay.setNeedSmsconfirm(smsNeed);
            orderPay.setUpdateTime(new Date());
            orderPayDao.updateById(orderPay);
            return ResultGenerator.genSuccessResult(jsonObject);
        }
        return ResultGenerator.genFailResult("支付未成功");

    }

    /**
     * 交易达成
     */
    @Override
    public Result tradeSettle(String orderId, Long userId){
        TbOrderPay orderPay = orderPayDao.selectOne(new QueryWrapper<TbOrderPay>()
                .eq("order_id", orderId)
                .eq("is_deleted", "0"));
        if(orderPay == null){
            return ResultGenerator.genFailResult("未检索到支付记录");
        }

        String outTradeNo = GetCodeUtil.getOrderId(userId);
        Map<String,String> bizContent = new HashMap<>();
        bizContent.put("orig_out_trade_no", orderPay.getOutTradeNo());
        bizContent.put("out_trade_no", outTradeNo);
        bizContent.put("royalty_info", null);

        RequestBase requestBase = genRequestBase(gson.toJson(bizContent), outTradeNo, KJTConstants.SERVICE_TRADE_SETTLE);
        ResponseParameter result = callKjt(requestBase);
        if(result == null){
            // 失败
            String failMsg = "达成失败，快捷通返回null";
            log.error(failMsg);
            orderPay.setFailMsg(result.getMsg());
            orderPay.setPayStatus(PayStatusEnum.PAY_FAIL.getPayStatus());
            orderPay.setUpdateTime(new Date());
            orderPayDao.updateById(orderPay);
            return ResultGenerator.genFailResult(failMsg);
        }else if(!ReturnInfoEnum.SUCCESS.getCode().equals(result.getCode())){
            // 失败
            String failMsg = StringUtils.isNotBlank(result.getSubMsg()) ? result.getSubMsg() : result.getMsg();
            log.error("交易达成失败: {}", failMsg);
            orderPay.setFailCode(result.getCode());
            orderPay.setFailMsg(result.getMsg());
            orderPay.setPayStatus(PayStatusEnum.PAY_FAIL.getPayStatus());
            orderPay.setUpdateTime(new Date());
            orderPayDao.updateById(orderPay);
            return ResultGenerator.genFailResult(failMsg);
        }else if(ReturnInfoEnum.SUCCESS.getCode().equals(result.getCode())){
            // 成功
            log.info("交易达成成功：{}", gson.toJson(result));
            JSONObject jsonObject = JSONObject.parseObject((String)result.getBizContent());

            orderPay.setPayStatus(PayStatusEnum.PAY_SUCCESS.getPayStatus());
            orderPay.setPayconfirmTime(new Date());
            orderPay.setUpdateTime(new Date());
            orderPayDao.updateById(orderPay);
            return ResultGenerator.genSuccessResult(jsonObject);
        }
        return ResultGenerator.genFailResult("交易达成未成功");
    }

    /**
     * 协议支付确认
     */
    @Override
    public Result agreementPayConfirm(Long userId, String orderId, String phoneCheckCode){
        TbOrderPay orderPay = orderPayDao.selectOne(new QueryWrapper<TbOrderPay>()
                .eq("order_id", orderId)
                .eq("is_deleted", "0"));
        if(orderPay == null){
            return ResultGenerator.genFailResult("未检索到支付记录");
        }
        String outTradeNo = String.valueOf(System.currentTimeMillis());
        Map<String,String> bizContent = new HashMap<>();
        bizContent.put("phone_check_code", phoneCheckCode);
        bizContent.put("pay_token", orderPay.getPayToken());

        RequestBase requestBase = genRequestBase(gson.toJson(bizContent), outTradeNo, KJTConstants.SERVICE_AGREEMENT_PAY_CONFIRM);
        ResponseParameter result = callKjt(requestBase);
        if(result == null){
            // 支付失败
            String failMsg = "协议支付确认失败，快捷通返回null";
            log.error(failMsg);
            orderPay.setFailMsg(failMsg);
            orderPay.setPayStatus(PayStatusEnum.PAY_FAIL.getPayStatus());
            orderPay.setUpdateTime(new Date());
            orderPayDao.updateById(orderPay);
            return ResultGenerator.genFailResult(failMsg);
        }else if(!ReturnInfoEnum.SUCCESS.getCode().equals(result.getCode())){
            // 支付失败
            String failMsg = StringUtils.isNotBlank(result.getSubMsg()) ? result.getSubMsg() : result.getMsg();
            log.error("协议支付确认失败: {}", failMsg);
            orderPay.setFailCode(result.getCode());
            orderPay.setFailMsg(failMsg);
            orderPay.setPayStatus(PayStatusEnum.PAY_FAIL.getPayStatus());
            orderPay.setUpdateTime(new Date());
            orderPayDao.updateById(orderPay);
            return ResultGenerator.genFailResult(failMsg);
        }else if(ReturnInfoEnum.SUCCESS.getCode().equals(result.getCode())){
            // 成功
            log.info("协议支付确认成功：{}", gson.toJson(result));
            JSONObject jsonObject = JSONObject.parseObject((String)result.getBizContent());
            // 签约成功保存token_id
            TbUserPay userPay = userPayDao.selectOne(new QueryWrapper<TbUserPay>().eq("user_id", userId));
            if(StringUtils.isNotBlank(userPay.getTokenId()) && userPay.getTokenIsvalid() != 1){
                userPay.setTokenIsvalid(1);
                userPay.setUpdateTime(new Date());
                userPayDao.updateById(userPay);
            }

            orderPay.setPayStatus(PayStatusEnum.PAY_WAIT_CONFIRM.getPayStatus());
            orderPay.setUpdateTime(new Date());
            orderPayDao.updateById(orderPay);
            return ResultGenerator.genSuccessResult(jsonObject);
        }
        return ResultGenerator.genFailResult("协议支付确认失败");
    }

    /**
     * 退款/ 担保撤销网关接口
     */
    @Override
    @Transactional
    public Result tradeRefund(String orderId, Long userId){
        TbOrderPay orderPay = orderPayDao.selectOne(new QueryWrapper<TbOrderPay>()
                .eq("order_id", orderId)
                .eq("is_deleted", "0"));
        if(orderPay == null){
            return ResultGenerator.genFailResult("未检索到支付记录");
        }

        TbPayRefund payRefund = payRefundDao.selectOne(new QueryWrapper<TbPayRefund>().eq("order_id", orderId));
        if(payRefund != null){
            return ResultGenerator.genFailResult("已申请过退款");
        }

        String outTradeNo = GetCodeUtil.getOrderId(userId);
        payRefund = new TbPayRefund();
        payRefund.setOrderId(orderPay.getOrderId());
        payRefund.setOrigOutTradeNo(orderPay.getOutTradeNo());
        payRefund.setOutTradeNo(outTradeNo);
        payRefund.setRefundAmount(orderPay.getPayMoney());
        payRefund.setRefundStatus(PayRefundStatusEnum.REFUND_Q.getCode());
        payRefund.setCreateTime(new Date());
        payRefund.setUpdateTime(new Date());
        payRefundDao.insert(payRefund);

        TradeRefundReq tradeRefundReq = new TradeRefundReq();
        tradeRefundReq.setOutTradeNo(outTradeNo);//退款流水
        tradeRefundReq.setOrigOutTradeNo(orderPay.getOutTradeNo());//原始订单号
        tradeRefundReq.setRefundAmount(orderPay.getPayMoney().toString());//退款金额
        tradeRefundReq.setNotifyUrl(kjtConfig.getTradeRefundAsyncNotify());//回调接口地址
        RequestBase requestBase = genRequestBase(gson.toJson(tradeRefundReq), outTradeNo, KJTConstants.SERVICE_TRADE_REFUND);
        ResponseParameter result = callKjt(requestBase);
        if(result == null){
            log.error("退款接口请求失败，返回null");

            // 更新退款表
            payRefund.setRefundStatus("F");
            payRefund.setFailMsg("退款接口请求失败，返回null");
            payRefund.setUpdateTime(new Date());
            payRefundDao.updateById(payRefund);
            return ResultGenerator.genFailResult("退款接口请求失败");
        }else if(!ReturnInfoEnum.SUCCESS.getCode().equals(result.getCode())){
            // 失败
            String failMsg = StringUtils.isNotBlank(result.getSubMsg()) ? result.getSubMsg() : result.getMsg();
            log.error("退款失败: {}", failMsg);

            // 更下退款表
            payRefund.setRefundStatus("F");
            payRefund.setFailMsg(failMsg);
            payRefund.setFailCode(result.getCode());
            payRefund.setUpdateTime(new Date());
            payRefundDao.updateById(payRefund);
            return ResultGenerator.genFailResult(failMsg);
        }else if(ReturnInfoEnum.SUCCESS.getCode().equals(result.getCode())){
            // 成功
            log.info("退款成功, 金额：{}", tradeRefundReq.getRefundAmount());
            JSONObject jsonObject = JSONObject.parseObject((String)result.getBizContent());

            // 更下退款表
            payRefund.setRefundStatus(jsonObject.getString("status"));
            payRefund.setTradeNo(jsonObject.getString("trade_no"));
            payRefund.setUpdateTime(new Date());
            payRefundDao.updateById(payRefund);
            return ResultGenerator.genSuccessResult(jsonObject);
        }
        return ResultGenerator.genFailResult("退款失败");
    }

    /**
     * 交易查询
     */
    @Override
    public Result tradeQuery(String orderId, String payType){
        TbOrderPay orderPay = orderPayDao.selectOne(new QueryWrapper<TbOrderPay>()
                .eq("order_id", orderId)
                .eq("is_deleted", "0")
                .eq("pay_type", payType));
        if(orderPay == null){
            return ResultGenerator.genFailResult("未检索到支付记录");
        }

        Map<String,String> reqMap = new HashMap<>();
        reqMap.put("out_trade_no", orderPay.getOutTradeNo());
        RequestBase requestBase = genRequestBase(gson.toJson(reqMap), orderPay.getOutTradeNo(), KJTConstants.SERVICE_TRADE_QUERY);
        ResponseParameter result = callKjt(requestBase);
        if(result == null){
            return ResultGenerator.genFailResult("交易查询接口请求失败");
        }else if(!ReturnInfoEnum.SUCCESS.getCode().equals(result.getCode())){
            // 支付失败
            String failMsg = StringUtils.isNotBlank(result.getSubMsg()) ? result.getSubMsg() : result.getMsg();
            log.error("交易查询接口请求失败: {}", failMsg);
            return ResultGenerator.genFailResult(failMsg);
        }else if(ReturnInfoEnum.SUCCESS.getCode().equals(result.getCode())){
            // 成功
            log.info("交易查询接口请求成功：{}", gson.toJson(result));
            JSONObject jsonObject = JSONObject.parseObject((String)result.getBizContent());
            // 签约成功保存token_id
            return ResultGenerator.genSuccessResult(jsonObject);
        }
        return ResultGenerator.genFailResult("交易查询接口请求失败");
    }

    /**
     * 支付状态变更异步通知
     */
    @Override
    @Transactional
    public Boolean ensureTradeAsyncNotify(EnsureTradeCallBackDto reqBean){
        log.info("接收到支付回调：{}", JSON.toJSONString(reqBean));
        log.info("支付状态：{}", reqBean.getTrade_status());
        /*VerifyResult verifyResult;
        if("RSA".equals(reqBean.getSignType())){
            //RSA验签
            Map signMap = KJTPayUtil.objToMap(reqBean);
            log.info("signMap: {}", signMap);
            verifyResult = securityService.verify(signMap, reqBean.getSign(), KJTConstants.DEFAULT_CHARSET);
        }else {
            throw new BusinessException("不支持的加密类型：" + reqBean.getSignType());
        }

        if(!verifyResult.isSuccess()){
            log.error("验签失败");
//            throw new BusinessException("验签失败");
        }*/

        insertRevLog(reqBean.getOuter_trade_no(), "ensureTradeAsyncNotify", JSON.toJSONString(reqBean), JSON.toJSONString(reqBean));
        // 更新支付状态
        TbOrderPay orderPay = orderPayDao.selectOne(new QueryWrapper<TbOrderPay>()
                .eq("out_trade_no", reqBean.getOuter_trade_no())
                .eq("is_deleted", "0"));
        if(orderPay == null){
            throw new BusinessException("未检索到支付记录");
        }

        if("WAIT_BUYER_PAY".equalsIgnoreCase(reqBean.getTrade_status())){
            // 等待买家付款
            orderPay.setPayStatus(PayStatusEnum.PAY_WAIT.getPayStatus());
        }else if("PAY_FINISHED".equalsIgnoreCase(reqBean.getTrade_status())){
            // 买家已付款
            // 更新订单状态为待发货
            OrderInfo info = new OrderInfo();
            info.setId(orderPay.getOrderId());
            info.setUpdateTime(new Date());
            info.setStatus(OrderStatusEnum.OREDER_PRE_OUT.getOrderStatus());
            orderInfoMapper.updateByPrimaryKeySelective(info);

            // 更新库存
            List<OrderGoodInfoVo> orderGoodInfoVos = orderGoodInfoMapper.selectByOrderId(orderPay.getOrderId());
            List<StockNumDTO> list = orderGoodInfoVos.stream().map(m -> {
                StockNumDTO stockNumDTO = new StockNumDTO();
                stockNumDTO.setGoodsId(m.getGoodId());
                stockNumDTO.setGoodsCount(m.getNumber());
                return stockNumDTO;
            }).collect(Collectors.toList());
            goodsMapper.updateStockNum(list);

            orderPay.setPayTime(new Date());
            orderPay.setPayStatus(PayStatusEnum.PAY_WAIT_CONFIRM.getPayStatus());
        }else if("TRADE_SUCCESS".equalsIgnoreCase(reqBean.getTrade_status())){
            // 交易支付成功
            orderPay.setPayconfirmTime(new Date());
            orderPay.setPayStatus(PayStatusEnum.PAY_SUCCESS.getPayStatus());
        }
        orderPay.setTradeNo(reqBean.getInner_trade_no());
        orderPay.setUpdateTime(new Date());
        orderPayDao.updateById(orderPay);

        return true;
    }

    /**
     * 退款异步通知
     */
    @Override
    @Transactional
    public Result tradeRefundAsyncNotify(Map<String, Object> params){
        log.info("接收到退款回调：{}", JSON.toJSONString(params));
        TbPayRefund payRefund = payRefundDao.selectOne(new QueryWrapper<TbPayRefund>()
                .eq("out_trade_no", params.get("outer_trade_no")));
        if(payRefund == null){
            return ResultGenerator.genFailResult("未检索到退款记录");
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 商户验签
     * @param verifyData
     * @return
     * @throws UnsupportedEncodingException
     */
    public boolean verify(String verifyData) {
        if("0".equalsIgnoreCase(kjtConfig.getVerifyFlag())){
            return true;
        }
        String privateKey = kjtConfig.getPrivatekey();
        String kjtPublicKey = kjtConfig.getKjtpublickey();
        SecurityService    securityService = new SecurityService(privateKey, kjtPublicKey);
        if(StringUtils.isNoneBlank(verifyData)){
            VerifyResult result = null;
            Gson gson = new Gson();
            Map<String, String> responseParameter = new HashMap<String, String>();
            responseParameter = gson.fromJson(verifyData, responseParameter.getClass());
            String bizContent = responseParameter.get("biz_content")==null ? null : JSON.toJSONString(responseParameter.get("biz_content"));
            responseParameter.remove("biz_content");
            responseParameter.put("biz_content", bizContent);

            String signType = responseParameter.get("sign_type");
            String charset = responseParameter.get("charset");
            if(StringUtils.isBlank(charset)){
                charset = responseParameter.get("_input_charset");
                if(StringUtils.isBlank(charset)){
                    charset = "UTF-8";
                }
            }
            if(StringUtils.isBlank(signType)){
                signType = "RSA";
            }
            String sign = responseParameter.get("sign");
            if("RSA".equals(signType)){
                result = securityService.verify(responseParameter, sign, charset);//RSA验签
            }
            if(result!=null){
                if(result.isSuccess()){
                    //System.out.println("验签通过");
                    return true;
                }else{
                    //System.out.println("验签失败！");
                    return false;
                }
            }else{
                //System.out.println("验签异常！");
                return false;
            }
        }
        //System.out.println("商户验签参数不正确！");
        return false;
    }

}
