package com.hailian.ylwmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.hailian.ylwmall.common.pay.KJTConstants;
import com.hailian.ylwmall.common.pay.PayStatusEnum;
import com.hailian.ylwmall.common.pay.ProductCodeEnum;
import com.hailian.ylwmall.config.KjtConstants;
import com.hailian.ylwmall.controller.vo.OrderGoodInfoVo;
import com.hailian.ylwmall.dto.pay.EnsureTradeBean;
import com.hailian.ylwmall.dto.pay.EnsureTradeReq;
import com.hailian.ylwmall.entity.TbOrderPay;
import com.hailian.ylwmall.entity.TbUserPay;
import com.hailian.ylwmall.entity.order.OrderInfo;
import com.hailian.ylwmall.service.PayService;
import com.hailian.ylwmall.util.ArithmeticUtil;
import com.hailian.ylwmall.util.GetCilentIP;
import com.hailian.ylwmall.util.GetCodeUtil;
import com.kjtpay.gateway.common.domain.VerifyResult;
import com.kjtpay.gateway.common.domain.base.RequestBase;
import com.kjtpay.gateway.common.domain.base.ResponseParameter;
import com.kjtpay.gateway.common.domain.instanttrade.TradeInfo;
import com.kjtpay.gateway.common.enums.ReturnInfoEnum;
import com.kjtpay.gateway.common.enums.Terminal;
import com.kjtpay.gateway.common.util.security.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付服务类
 * @author 19033323
 */
@Slf4j
@Service
public class PayServiceImpl extends PayServiceBase implements PayService {

    /*public RequestBase ensureTradeOld(String outTradeNo){
        // 交易信息
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setOutTradeNo(outTradeNo);//平台（商户）单号
        tradeInfo.setSubject("苹果");//商品名称
        tradeInfo.setPrice(ArithmeticUtil.strRound("0.19",2));//单价，精确到两位小数 5000.00
        tradeInfo.setEnsureAmount(ArithmeticUtil.strRound("0.19",2));
        tradeInfo.setQuantity("1");//数量
        tradeInfo.setTotalAmount(ArithmeticUtil.strRound("0.19",2));//交易金额
        tradeInfo.setPayeeIdentityType("1");
        tradeInfo.setPayeeIdentity(kjtConfig.getPayeeidentity());//卖家会员id或登录账号
        tradeInfo.setNotifyUrl(kjtConfig.getInstantTradeAsyncNotify());//服务器异步通知地址
        System.out.println("tradeInfo: " + gson.toJson(tradeInfo));

        // 业务信息
        EnsureTradeBizContent tradeBizContent = new EnsureTradeBizContent();
        tradeBizContent.setPayerIdentityType(KjtConstants.PayerIdentityType.PAYER_IDENTITY_TYPE_1);
        tradeBizContent.setPayerPlatformType(KjtConstants.InstantTrade.BizContent.PAYER_PLATFORM_TYPE);
        tradeBizContent.setPayerIdentity(KjtConstants.InstantTrade.BizContent.PAYER_IDENTITY_ID);
        tradeBizContent.setPayerIp("223.80.102.178");//买家公网ip
        tradeBizContent.setBizProductCode("20702");
        tradeBizContent.setCashierType("WEB");
        tradeBizContent.setTimeoutExpress("2h");//订单付款码2h有效
        tradeBizContent.setTradeInfo(gson.toJson(tradeInfo));
        // 支付方式设置
        String payMethodTempt;
//        JSONObject payMethodJson = new JSONObject();//支付方式
//        payMethodJson.put("pay_product_code","65");
//        payMethodJson.put("amount",ArithmeticUtil.strRound(totalAmount,2));
//        payMethodJson.put("target_organization","WECHAT");
//        payMethodTempt = payMethodJson.toJSONString();
        tradeBizContent.setPayMethod("");
        // 终端信息设置
        Map<String,String> terminal_info = new HashMap<>();
        terminal_info.put("terminal_type","01");//手机
        terminal_info.put("ip","223.80.102.178");
        tradeBizContent.setTerminalInfo(gson.toJson(terminal_info));
        String bizContent = gson.toJson(tradeBizContent);
        // 嵌套json处理
        log.info("bizContent转换前：{}", bizContent);
        JSONObject bizReq = null;
        bizReq = KJTPayUtil.convertParm(KJTConstants.SERVICE_ENSURE_TRADE, bizContent);
        // 私钥加密
        log.info(">biz_content加密前:"+bizReq);
        String bizContentEncryptStr = securityService.encrypt(bizReq.toJSONString(), ReqValue.DEFAULT_CHARSET); //业务数据加密
        log.info("biz_content加密后:"+bizContentEncryptStr);

        // 最终请求构建及签名
        RequestBase requestBase = genRequestBase(bizContentEncryptStr, kjtConfig.getPayeeidentity(),outTradeNo);
        String signData = new Gson().toJson(requestBase);
        Map<String,String> req = JSON.parseObject(signData, HashMap.class);
        System.out.println("3.1-->商户签名前："+signData);
        String signDataStr = securityService.sign(req, ReqValue.DEFAULT_CHARSET);
        requestBase.setSign(signDataStr);

        String result = new Gson().toJson(requestBase);
        log.info("ensure_trade签名后数据："+result);
        return requestBase;
    }*/

    /**
     * 担保支付
     */
    @Override
    public RequestBase ensureTrade(EnsureTradeReq reqBean, HttpServletRequest request){
        // 生成支付订单号
        String ip = GetCilentIP.getIpAddr(request);
        Long userId = (Long)request.getSession().getAttribute("loginUserId");
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(Long.parseLong(reqBean.getOrderId()));
        List<OrderGoodInfoVo> orderGoodInfoVos = orderGoodInfoMapper.selectByOrderId(orderInfo.getId());
        List<TbOrderPay> orderPayList = orderPayDao.selectList(new QueryWrapper<TbOrderPay>().eq("order_id", orderInfo.getId()));
        TbOrderPay orderPay = null;
        if(orderPayList == null || orderPayList.isEmpty()){
            String outTradeNo = GetCodeUtil.getOrderId(userId);
            // 保存支付表
            orderPay = new TbOrderPay();
            orderPay.setOrderId(orderInfo.getId());
            orderPay.setOutTradeNo(outTradeNo);
            orderPay.setPayStatus(PayStatusEnum.PAY_ING.getPayStatus());
            orderPay.setPayTime(new Date());
            orderPayDao.insert(orderPay);
        }else {
            orderPay = orderPayList.get(0);
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
        tradeInfo.setNotifyUrl(kjtConfig.getInstantTradeAsyncNotify());//服务器异步通知地址

        // 业务信息
        EnsureTradeBean tradeBizContent = new EnsureTradeBean();
        tradeBizContent.setPayerIdentityType(KjtConstants.PayerIdentityType.PAYER_IDENTITY_TYPE_1);
        tradeBizContent.setPayerPlatformType(KjtConstants.InstantTrade.BizContent.PAYER_PLATFORM_TYPE);
        tradeBizContent.setPayerIdentity(KjtConstants.InstantTrade.BizContent.PAYER_IDENTITY_ID);
        tradeBizContent.setPayerIp(ip);
        tradeBizContent.setBizProductCode(ProductCodeEnum.ENSURE_TRADE_20702.getCode());
        tradeBizContent.setCashierType("WEB");
        tradeBizContent.setTimeoutExpress("2h");//订单付款码2h有效
        tradeBizContent.setTradeInfo(tradeInfo);
        // 支付方式设置
        tradeBizContent = setPay(tradeBizContent, reqBean, userId, orderInfo.getRealPrice());
        // 终端信息设置
        Map<String,String> terminalInfo = new HashMap<>();
        terminalInfo.put("terminal_type", Terminal.computer.getCode());//电脑
        terminalInfo.put("ip",ip);
        tradeBizContent.setTerminalInfo(terminalInfo);

        return genRequestBase(gson.toJson(tradeBizContent), orderPay.getOutTradeNo(), KJTConstants.SERVICE_ENSURE_TRADE);
    }

    /**
     * 协议支付
     * @param reqBean
     * @param request
     * @return
     */
    @Override
    public String ensureTradeAgreement(EnsureTradeReq reqBean, HttpServletRequest request){
        // 生成支付订单号
        String ip = GetCilentIP.getIpAddr(request);
        Long userId = (Long)request.getSession().getAttribute("loginUserId");
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(Long.parseLong(reqBean.getOrderId()));
        List<OrderGoodInfoVo> orderGoodInfoVos = orderGoodInfoMapper.selectByOrderId(orderInfo.getId());
        List<TbOrderPay> orderPayList = orderPayDao.selectList(new QueryWrapper<TbOrderPay>().eq("order_id", orderInfo.getId()));
        TbOrderPay orderPay = null;
        if(orderPayList == null || orderPayList.isEmpty()){
            String outTradeNo = GetCodeUtil.getOrderId(userId);
            // 保存支付表
            orderPay = new TbOrderPay();
            orderPay.setPayType(Integer.parseInt(reqBean.getPayType()));
            orderPay.setOrderId(orderInfo.getId());
            orderPay.setOutTradeNo(outTradeNo);
            orderPay.setPayStatus(PayStatusEnum.PAY_ING.getPayStatus());
            orderPay.setPayTime(new Date());
            orderPayDao.insert(orderPay);
        }else {
            orderPay = orderPayList.get(0);
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
        tradeInfo.setNotifyUrl(kjtConfig.getInstantTradeAsyncNotify());//服务器异步通知地址

        // 业务信息
        EnsureTradeBean tradeBizContent = new EnsureTradeBean();
        tradeBizContent.setPayerIdentityType(KjtConstants.PayerIdentityType.PAYER_IDENTITY_TYPE_1);
        tradeBizContent.setPayerPlatformType(KjtConstants.InstantTrade.BizContent.PAYER_PLATFORM_TYPE);
        tradeBizContent.setPayerIdentity(KjtConstants.InstantTrade.BizContent.PAYER_IDENTITY_ID);
        tradeBizContent.setPayerIp(ip);
        tradeBizContent.setBizProductCode(ProductCodeEnum.ENSURE_TRADE_20702.getCode());
        tradeBizContent.setCashierType("WEB");
        tradeBizContent.setTimeoutExpress("2h");//订单付款码2h有效
        tradeBizContent.setTradeInfo(tradeInfo);
        // 支付方式设置
        tradeBizContent = setPay(tradeBizContent, reqBean, userId, orderInfo.getRealPrice());
        // 终端信息设置
        Map<String,String> terminalInfo = new HashMap<>();
        terminalInfo.put("terminal_type", Terminal.computer.getCode());//电脑
        terminalInfo.put("ip",ip);
        tradeBizContent.setTerminalInfo(terminalInfo);

        RequestBase requestBase = genRequestBase(gson.toJson(tradeBizContent), orderPay.getOutTradeNo(), KJTConstants.SERVICE_ENSURE_TRADE);
        ResponseParameter result = callKjt(requestBase);
        if(result == null || !ReturnInfoEnum.SUCCESS.getCode().equals(result.getCode())){
            // 支付失败
            orderPay.setFailCode(result.getCode());
            orderPay.setFailMsg(result.getMsg());
            orderPay.setPayStatus(PayStatusEnum.PAY_FAIL.getPayStatus());
            orderPay.setUpdateTime(new Date());
            orderPayDao.updateById(orderPay);
        }else if(ReturnInfoEnum.SUCCESS.getCode().equals(result.getCode())){
            // 支付成功
            JSONObject jsonObject = JSONObject.parseObject((String)result.getBizContent());

            // 签约成功保存token_id
            TbUserPay userPay = userPayDao.selectOne(new QueryWrapper<TbUserPay>().eq("user_id", userId));
            if(StringUtils.isBlank(userPay.getTokenId()) && StringUtils.isNotBlank(jsonObject.getString("token_id"))){
                userPay.setTokenId(jsonObject.getString("token_id"));
                userPay.setUpdateTime(new Date());
                userPayDao.updateById(userPay);
            }
            orderPay.setPayStatus(PayStatusEnum.PAY_WAIT.getPayStatus());
            if(StringUtils.isNotBlank(jsonObject.getString("pay_token"))){
                orderPay.setTokenId(jsonObject.getString("token_id"));
                orderPay.setPayToken(jsonObject.getString("pay_token"));
            }
            orderPay.setTradeNo(jsonObject.getString("trade_no"));
            orderPay.setNeedSmsconfirm(jsonObject.getString("status"));
            orderPay.setUpdateTime(new Date());
            orderPayDao.updateById(orderPay);
        }

        return gson.toJson(result);
    }

    @Override
    public ResponseParameter tradeSettle(String origOutTradeNo){
        String outTradeNo = String.valueOf(System.currentTimeMillis());
        Map<String,String> bizContent = new HashMap<>();
        bizContent.put("orig_out_trade_no", origOutTradeNo);
        // todo 交易号待生成
        bizContent.put("out_trade_no", outTradeNo);
        bizContent.put("royalty_info", null);

        RequestBase requestBase = genRequestBase(gson.toJson(bizContent), outTradeNo, KJTConstants.SERVICE_TRADE_SETTLE);
        ResponseParameter result = callKjt(requestBase);
        return result;
    }

    @Override
    public ResponseParameter agreementPayConfirm(String payToken, String phoneCheckCode){
        String outTradeNo = String.valueOf(System.currentTimeMillis());
        Map<String,String> bizContent = new HashMap<>();
        bizContent.put("phone_check_code", phoneCheckCode);
        bizContent.put("pay_token", payToken);

        RequestBase requestBase = genRequestBase(gson.toJson(bizContent), outTradeNo, KJTConstants.SERVICE_AGREEMENT_PAY_CONFIRM);
        ResponseParameter result = callKjt(requestBase);
        return result;
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
