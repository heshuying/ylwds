package com.hailian.ylwmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import com.hailian.ylwmall.config.KjtConfig;
import com.hailian.ylwmall.config.KjtConstants;
import com.hailian.ylwmall.dto.InstantTradeBizContent;
import com.hailian.ylwmall.dto.MemberQuicklyRegisterReq;
import com.hailian.ylwmall.dto.TradeCloseBizContent;
import com.hailian.ylwmall.util.ArithmeticUtil;
import com.hailian.ylwmall.util.RestUtils;
import com.kjtpay.gateway.common.domain.VerifyResult;
import com.kjtpay.gateway.common.domain.base.RequestBase;
import com.kjtpay.gateway.common.domain.base.ResponseParameter;
import com.kjtpay.gateway.common.domain.instanttrade.TradeInfo;
import com.kjtpay.gateway.common.domain.traderefund.TradeRefundReq;
import com.kjtpay.gateway.common.domain.transfertoaccount.TransferToAccountReq;
import com.kjtpay.gateway.common.util.JsonMapUtil;
import com.kjtpay.gateway.common.util.security.SecurityService;
import com.hailian.ylwmall.dto.TransferToCardBizContent;
import com.hailian.ylwmall.service.KjtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 01440590 on 2019/1/22.
 */
@Component
@Service
public class KjtServiceImpl implements KjtService {
    @Autowired
    private KjtConfig kjtConfig;

    @Override
    public String instantTrade(String outTradeNo,String subject,String price,String quantity,String totalAmount,
                               String payerIp,String payMethod) {
        String service = "instant_trade";
        //=====================================1.生成交易详细信息参数=============================================
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setOutTradeNo(outTradeNo);//平台（商户）单号
        tradeInfo.setSubject(subject);//商品名称
        tradeInfo.setCurrency(KjtConstants.CurrencyType.CNY);
        tradeInfo.setPrice(ArithmeticUtil.strRound(price,2));//单价，精确到两位小数 5000.00
        tradeInfo.setQuantity(quantity);//数量
        tradeInfo.setTotalAmount(ArithmeticUtil.strRound(totalAmount,2));//交易金额
        tradeInfo.setPayeeIdentityType("1");
        tradeInfo.setPayeeIdentity(kjtConfig.getPayeeidentity());//卖家会员id或登录账号
        tradeInfo.setNotifyUrl(kjtConfig.getInstantTradeAsyncNotify());//服务器异步通知地址
        //tradeInfo.setRoyalty_info("");//分润账号
        String tradeInfoStr= new Gson().toJson(tradeInfo);
//        System.out.println("1-->交易详细信息参数-->trade_info-->无需加密-->放在业务参数中:"+tradeInfoStr);

        //===================================2.生成业务数据====================================================
        InstantTradeBizContent instantTradeBizContent = new InstantTradeBizContent();
        instantTradeBizContent.setPayerIdentityType(KjtConstants.PayerIdentityType.PAYER_IDENTITY_TYPE_1);
        instantTradeBizContent.setPayerPlatformType(KjtConstants.InstantTrade.BizContent.PAYER_PLATFORM_TYPE);
        instantTradeBizContent.setPayerIdentity(KjtConstants.InstantTrade.BizContent.PAYER_IDENTITY_ID);
        instantTradeBizContent.setPayerIp(payerIp);//买家公网ip
        String payMethodTempt;

        JSONObject payMethodJson = new JSONObject();//支付方式
        switch (payMethod){
            case "03"://"pay_method":{"amount":"0.19","target_organization":"WECHAT","pay_product_code":"65"}这个是微信的，微信是65
                payMethodJson.put("pay_product_code","65");
                payMethodJson.put("amount",ArithmeticUtil.strRound(totalAmount,2));
                payMethodJson.put("target_organization","WECHAT");
                break;//微信
            case "04"://"pay_method":{"amount":"0.19","target_organization":"ALIPAY","pay_product_code":"74"}这个是支付宝的，支付宝是74
                payMethodJson.put("pay_product_code","65");
                payMethodJson.put("amount",ArithmeticUtil.strRound(totalAmount,2));
                payMethodJson.put("target_organization","ALIPAY");
                break;//支付宝
            default:
                payMethodJson.put("pay_product_code","65");
                payMethodJson.put("amount",ArithmeticUtil.strRound(totalAmount,2));
                payMethodJson.put("target_organization","WECHAT");
        }
        payMethodTempt = payMethodJson.toJSONString();
        instantTradeBizContent.setPayMethod(payMethodTempt);//KjtConstants.InstantTrade.BizContent.PayMethod
        instantTradeBizContent.setBizProductCode(KjtConstants.InstantTrade.BizContent.BizProductCode.BIZ_PRODUCT_CODE_20601);
        instantTradeBizContent.setCashierType(KjtConstants.InstantTrade.BizContent.CashierType.API);
        instantTradeBizContent.setTimeoutExpress("2h");//订单付款码2h有效
        instantTradeBizContent.setTradeInfo(tradeInfoStr);
        Map<String,String> terminal_info = new HashMap<>();//"terminal_type":"01","ip":"122.224.203.210"}
        terminal_info.put("terminal_type","01");//手机
        terminal_info.put("ip",payerIp);
        String json = new Gson().toJson(terminal_info);
        instantTradeBizContent.setTerminalInfo(json);
        String bizContent = new Gson().toJson(instantTradeBizContent);
//        System.out.println("2.1-->业务参数-->biz_content-->加密前:"+bizContent);
        String bizContentEncryptStr = encrypt(bizContent,service);//业务数据加密
        //System.out.println("2.2-->业务参数-->biz_content-->加密后:"+bizContentEncryptStr);

        //====================================3.生成请求公共参数===============================================
        RequestBase requestBase = getRequestBase(outTradeNo,service,bizContentEncryptStr);
        String signData = new Gson().toJson(requestBase);
//        System.out.println("3.1-->商户签名前："+signData);
        String signDataStr = sign(signData); //商户签名
        //System.out.println("3.2-->商户签名后："+signDataStr);

        //====================================4.组织最终参数====================================
        requestBase.setSign(signDataStr);
        net.sf.json.JSONObject jsonObjectFinal = getJsonObjectFinal(requestBase);
        //System.out.println("4-->最终请求参数："+jsonObjectFinal.toString());

        //=================================5.调用网关接口=================================
        String url = kjtConfig.getRecv();
        String result = sendRest(jsonObjectFinal,url);
//        System.out.println("6-->本地方法返回值："+result);
        return result;
    }

    @Override
    public String transferToCard(String outTradeNo, String amount, String bankCardNo, String bankAccountName, String bankCode,
                                 String bankName, String bankBranchName,String productCode) {
        String service = "transfer_to_card";
        //============================================1.生成业务数据:start=========================================================
        TransferToCardBizContent transferToCardBizContent = new TransferToCardBizContent();
        transferToCardBizContent.setOutTradeNo(outTradeNo);//平台(商户)订单号
        transferToCardBizContent.setPayerIdentityType(KjtConstants.PayerIdentityType.PAYER_IDENTITY_TYPE_1);//出款快捷通会员标识类型，默认1
        transferToCardBizContent.setPayerIdentity(kjtConfig.getPayeeidentity());//出款账号
        transferToCardBizContent.setAmount(ArithmeticUtil.strRound(amount,2));//出款金额
        transferToCardBizContent.setCurrency("CNY");
        transferToCardBizContent.setBankCardNo(bankCardNo);//银行卡号
        transferToCardBizContent.setBankAccountName(bankAccountName);//银行卡账户名
        transferToCardBizContent.setBankCode(bankCode);//银行编码，字母
        transferToCardBizContent.setBankName(bankName);//银行名称
        transferToCardBizContent.setBankBranchName(bankBranchName);//银行分支行名称
        transferToCardBizContent.setPayProductCode(productCode);//支付产品码 14-付款到银行卡-对私 15-付款到银行卡-对公
        transferToCardBizContent.setBizProductCode("10221");//业务产品码 10220-付款到卡（次日） 10221-付款到卡（普通）两小时到账
        transferToCardBizContent.setNotifyUrl(kjtConfig.getTransferToCardAsyncNotify());//通知地址

        String bizContent = new Gson().toJson(transferToCardBizContent);
        //System.out.println("1.1-->业务参数-->biz_content-->加密前:"+bizContent);
        String bizContentEncryptStr = encrypt(bizContent,service);//业务数据加密
        //System.out.println("1.2-->业务参数-->biz_content-->加密后:"+bizContentEncryptStr);
        //============================================1.生成业务数据:end=========================================================

        //============================================2.生成请求公共参数:start=========================================================
        RequestBase requestBase = getRequestBase(outTradeNo,service,bizContentEncryptStr);
        String signData = new Gson().toJson(requestBase);
        //System.out.println("3.1-->商户签名前："+signData);
        String signDataStr = sign(signData);//商户签名
        //System.out.println("3.2-->商户签名后："+signDataStr);
        //============================================2.生成请求公共参数:end=========================================================

        //============================================3.组织最终参数:start=========================================================
        requestBase.setSign(signDataStr);
        net.sf.json.JSONObject jsonObjectFinal = getJsonObjectFinal(requestBase);
        //System.out.println("4-->最终请求参数："+jsonObjectFinal.toString());
        //============================================3.组织最终参数:end=========================================================

        //============================================4.调用网关接口========================================================
        String url = kjtConfig.getRecv();
        String result = sendRest(jsonObjectFinal,url);
        //System.out.println("6-->本地方法返回值："+result);
        return result;
    }

    @Override
    public String tradeClose(String outTradeNo,String tradeNo) {
        String service = "trade_close";
        //============================================1.生成业务数据:start=========================================================
        TradeCloseBizContent tradeCloseBizContent = new TradeCloseBizContent();
        if(StringUtils.isNotBlank(outTradeNo)){
            tradeCloseBizContent.setOutTradeNo(outTradeNo);
        }else if(StringUtils.isNotBlank(tradeNo)){
            tradeCloseBizContent.setTradeNo(tradeNo);
        }
        String bizContent = new Gson().toJson(tradeCloseBizContent);
        //System.out.println("1.1-->业务参数-->biz_content-->加密前:"+bizContent);
        String bizContentEncryptStr = encrypt(bizContent,service);//业务数据加密
        //System.out.println("1.2-->业务参数-->biz_content-->加密后:"+bizContentEncryptStr);
        //============================================1.生成业务数据:end=========================================================

        //============================================2.生成请求公共参数:start=========================================================
        RequestBase requestBase = getRequestBase(outTradeNo,service,bizContentEncryptStr);
        String signData = new Gson().toJson(requestBase);
        //System.out.println("3.1-->商户签名前："+signData);
        String signDataStr = sign(signData);//商户签名
        //System.out.println("3.2-->商户签名后："+signDataStr);
        //============================================2.生成请求公共参数:end=========================================================

        //====================================3.组织最终参数====================================
        requestBase.setSign(signDataStr);
        net.sf.json.JSONObject jsonObjectFinal = getJsonObjectFinal(requestBase);
        //System.out.println("4-->最终请求参数："+jsonObjectFinal.toString());

        //=================================5.调用网关接口=================================
        String url = kjtConfig.getRecv();
        String result = sendRest(jsonObjectFinal,url);
        //System.out.println("6-->本地方法返回值："+result);
        return result;
    }

    @Override
    public String tradeRefund(String outTradeNo, String origOutTradeNo, String refundAmount) {
        String service = "trade_refund";
        //============================================1.生成业务数据:start=========================================================
        TradeRefundReq tradeRefundReq = new TradeRefundReq();
        tradeRefundReq.setOutTradeNo(outTradeNo);//退款流水
        tradeRefundReq.setOrigOutTradeNo(origOutTradeNo);//原始订单号
        tradeRefundReq.setRefundAmount(ArithmeticUtil.strRound(refundAmount,2));//退款金额
        tradeRefundReq.setNotifyUrl(kjtConfig.getTradeRefundAsyncNotify());//回调接口地址
        String bizContent = new Gson().toJson(tradeRefundReq);
        //System.out.println("1.1-->业务参数-->biz_content-->加密前:"+bizContent);
        String bizContentEncryptStr = encrypt(bizContent,service);//业务数据加密
        //System.out.println("1.2-->业务参数-->biz_content-->加密后:"+bizContentEncryptStr);
        //============================================1.生成业务数据:end=========================================================

        //============================================2.生成请求公共参数:start=========================================================
        RequestBase requestBase = getRequestBase(outTradeNo,service,bizContentEncryptStr);
        String signData = new Gson().toJson(requestBase);
        //System.out.println("3.1-->商户签名前："+signData);
        String signDataStr = sign(signData);//商户签名
        //System.out.println("3.2-->商户签名后："+signDataStr);
        //============================================2.生成请求公共参数:end=========================================================

        //====================================3.组织最终参数====================================
        requestBase.setSign(signDataStr);
        net.sf.json.JSONObject jsonObjectFinal = getJsonObjectFinal(requestBase);
        //System.out.println("4-->最终请求参数："+jsonObjectFinal.toString());

        //=================================5.调用网关接口=================================
        String url = kjtConfig.getRecv();
        String result = sendRest(jsonObjectFinal,url);
        //System.out.println("6-->本地方法返回值："+result);
        return result;
    }

    @Override
    public String memberQuicklyRegister(String outOrderNo, String name, String idCard, String mobile, String merchantName) {
        String service = "member_quickly_register";
        //============================================1.生成业务数据:start=========================================================
        MemberQuicklyRegisterReq memberQuicklyRegisterReq = new MemberQuicklyRegisterReq();
        memberQuicklyRegisterReq.setOuterOrderNo(outOrderNo);//商户流水号
        memberQuicklyRegisterReq.setName(name);//姓名
        memberQuicklyRegisterReq.setIdCard(idCard);//省份证号
        memberQuicklyRegisterReq.setMobile(mobile);//手机号
        memberQuicklyRegisterReq.setNeedSendMsn("1");//是否发短信
        memberQuicklyRegisterReq.setMerchantName(merchantName);//昵称
        memberQuicklyRegisterReq.setNeedOpenFund("2");//不开天天聚
        //============================================1.生成业务数据:end=========================================================

        //============================================2.生成请求公共参数:start=========================================================
        memberQuicklyRegisterReq.setService(service);
        memberQuicklyRegisterReq.setVersion("1.0");
        memberQuicklyRegisterReq.setPartnerId(kjtConfig.getPayeeidentity());
        memberQuicklyRegisterReq.setInputCharset("utf-8");
        memberQuicklyRegisterReq.setReturnUrl("");
        memberQuicklyRegisterReq.setMemo("");
        String signData = new Gson().toJson(memberQuicklyRegisterReq);
//        System.out.println("3.1-->商户签名前："+signData);
        String signDataStr = sign(signData);//商户签名
//        System.out.println("3.2-->商户签名后："+signDataStr);
        //============================================2.生成请求公共参数:end=========================================================

        //====================================3.组织最终参数====================================
        memberQuicklyRegisterReq.setSignType("RSA");
        memberQuicklyRegisterReq.setSign(signDataStr);
        net.sf.json.JSONObject jsonObjectFinal = getJsonObjectFinal(memberQuicklyRegisterReq);
//        System.out.println("4-->最终请求参数："+jsonObjectFinal.toString());

        //=================================5.调用网关接口=================================
        String url = kjtConfig.getRecvOrder();
        ResponseParameter responseParameter = new ResponseParameter();
        responseParameter.setCode("000000");
        responseParameter.setMsg("业务处理失败");
        responseParameter.setSubMsg("业务处理失败");
        try {
            String resultTempt = RestUtils.httpPostWithJson(jsonObjectFinal,url);
//            System.out.println("5-->返回值："+resultTempt);
            JSONObject resultMap = getPatamterToMap(resultTempt);
            String isSuccess = resultMap.getString("is_success");
            if("T".equals(isSuccess)){
                String errorCode = resultMap.getString("error_code");//错误码
                if(StringUtils.isNotBlank(errorCode)){
                    String errorMessage = resultMap.getString("error_message");//错误码
                    responseParameter.setSubCode(errorCode);
                    responseParameter.setSubMsg(errorMessage);
                }else{
                    String openStatus = resultMap.getString("open_status");//开户状态:1.成功；2.失败
                    if("1".equals(openStatus)){
                        responseParameter.setCode("S10000");
                        responseParameter.setSubMsg("开户成功");
                    }else{
                        String openStatusReason = resultMap.getString("open_status_reason");//开户失败原因
                        responseParameter.setSubMsg("开户失败:"+openStatusReason);
                    }
                }
            }else{
                responseParameter.setSubMsg("开户失败");
            }
        }catch (Exception e){
        }
        String result = new Gson().toJson(responseParameter);
//        System.out.println("6-->本地方法返回值："+result);
        return result;
    }

    @Override
    public String transferToAccount(String outTradeNo, String payeeIdentity, String transferAmount) {
        String service = "transfer_to_account";
        //============================================1.生成业务数据:start=========================================================
        TransferToAccountReq transferToAccountReq = new TransferToAccountReq();
        transferToAccountReq.setOutTradeNo(outTradeNo);//退款流水
        transferToAccountReq.setPayerIdentity("1");//出款快捷通会员标识类型，默认1  1-快捷通会员ID 2-快捷通会员登录号
        transferToAccountReq.setPayerIdentity(kjtConfig.getPayeeidentity());//出款账号
        transferToAccountReq.setPayeeIdentityType("2");//入款快捷通会员标识类型，默认1  1-快捷通会员ID 2-快捷通会员登录号
        transferToAccountReq.setPayeeIdentity(payeeIdentity);//入款账号
        transferToAccountReq.setBizProductCode(kjtConfig.getProductCode());//	业务产品码 10310-会员转账（实时） 10311-会员转账（普通） 10312-会员转账（次日）
        transferToAccountReq.setTransferAmount(ArithmeticUtil.strRound(transferAmount,2));//转账金额
        transferToAccountReq.setNotifyUrl(kjtConfig.getTransferToAccountAsyncNotify());//回调接口地址
        String bizContent = new Gson().toJson(transferToAccountReq);
        //System.out.println("1.1-->业务参数-->biz_content-->加密前:"+bizContent);
        String bizContentEncryptStr = encrypt(bizContent,service);//业务数据加密
        //System.out.println("1.2-->业务参数-->biz_content-->加密后:"+bizContentEncryptStr);
        //============================================1.生成业务数据:end=========================================================

        //============================================2.生成请求公共参数:start=========================================================
        RequestBase requestBase = getRequestBase(outTradeNo,service,bizContentEncryptStr);
        String signData = new Gson().toJson(requestBase);
        //System.out.println("3.1-->商户签名前："+signData);
        String signDataStr = sign(signData);//商户签名
        //System.out.println("3.2-->商户签名后："+signDataStr);
        //============================================2.生成请求公共参数:end=========================================================

        //====================================3.组织最终参数====================================
        requestBase.setSign(signDataStr);
        net.sf.json.JSONObject jsonObjectFinal = getJsonObjectFinal(requestBase);
        //System.out.println("4-->最终请求参数："+jsonObjectFinal.toString());

        //=================================5.调用网关接口=================================
        String url = kjtConfig.getRecv();
        String result = sendRest(jsonObjectFinal,url);
        //System.out.println("6-->本地方法返回值："+result);
        return result;
    }

    /**
     * 获取公共参数
     * @param outTradeNo
     * @param service
     * @param bizContentEncryptStr
     * @return
     */
    private RequestBase getRequestBase(String outTradeNo,String service,String bizContentEncryptStr){
        RequestBase requestBase = new RequestBase();
        requestBase.setRequestNo(outTradeNo);
        requestBase.setService(service);
        requestBase.setVersion("1.0");
        requestBase.setPartnerId(kjtConfig.getPayeeidentity());
        requestBase.setCharset("UTF-8");
        requestBase.setSignType("RSA");
        requestBase.setFormat("JSON");
        requestBase.setSign("");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        requestBase.setTimestamp(simpleDateFormat.format(new Date()));
        requestBase.setBizContent(bizContentEncryptStr);
        return requestBase;
    }

    /**
     * 组织最终请求参数
     * @param requestBase
     * @return
     */
    private net.sf.json.JSONObject getJsonObjectFinal (Object requestBase){
        net.sf.json.JSONObject jsonObjectFinal = new net.sf.json.JSONObject();
        String queryJsonString = new Gson().toJson(requestBase);
        JSONObject jsonObject = JSONObject.parseObject(queryJsonString);
        Set<String> keySet = jsonObject.keySet();
        for(String key:keySet){
            String value = jsonObject.getString(key);
            try {
                value = URLEncoder.encode(value,"UTF-8");
            }catch (Exception e){
                e.printStackTrace();
            }
            jsonObjectFinal.put(key,value);
        }
        return jsonObjectFinal;
    }

    /**
     * 发送rest请求，处理返回值
     * @param jsonObjectFinal
     * @return
     */
    private String sendRest(net.sf.json.JSONObject jsonObjectFinal,String url){
        ResponseParameter responseParameter = new ResponseParameter();
        responseParameter.setCode("000000");
        responseParameter.setMsg("业务处理失败");
        responseParameter.setSubMsg("业务处理失败");
        try {
            String resultTempt = RestUtils.httpPostWithJson(jsonObjectFinal,url);
            //System.out.println("5-->返回值："+resultTempt);
            boolean verifyFlag = this.verify(resultTempt);//验签
            if(verifyFlag){
                responseParameter = JsonMapUtil.gsonToObj(resultTempt, ResponseParameter.class);
            }else {
                responseParameter.setSubMsg("商户验签失败");
            }
        }catch (Exception e){
        }
        //清空不必要的参数
        responseParameter.setSign("");
        responseParameter.setCharset("");
        responseParameter.setSignType("");
        String result = new Gson().toJson(responseParameter);
        return result;
    }

    /**
     * 加密
     * @param req
     * @param service
     * @return
     */
    public  String encrypt(String req,String service) {
        String privateKey= kjtConfig.getPrivatekey();
        String kjtPublicKey= kjtConfig.getKjtpublickey();
        SecurityService    securityService = new SecurityService(privateKey, kjtPublicKey);
        //System.out.println("转换前json："+req);
        if(StringUtils.isNotBlank(req)){
            com.alibaba.fastjson.JSONObject bizReq = null;
            //因js转出的嵌套json有，使用gson转成请求类会报错，故需要转换一下
            if("instant_trade".equals(service)){
                bizReq = convertInstantTradeParm(req);
                if(bizReq != null){
                    //System.out.println("转换后json："+bizReq.toString());
                    //使用json字符串形式加密-RSA加密
                    String str= securityService.encrypt(bizReq.toString(), "UTF-8");
                    //System.out.println("业务数据加密后："+str);
                    return str;
                }
            }else if("transfer_to_card".equals(service)
                    || "trade_close".equals(service)
                    || "trade_refund".equals(service)
                    || "transfer_to_account".equals(service)){
                bizReq = JSONObject.parseObject(req);
                String str= securityService.encrypt(bizReq.toString(), "UTF-8");
                //System.out.println("业务数据加密后："+str);
                return str;
            }
        }
        return "请求参数填写出错或必填参数未填写";
    }

    /**
     * 商户签名
     * @param signData
     * @return
     */
    public String sign( String signData) {
        if(StringUtils.isNotBlank(signData)){
            String privateKey = kjtConfig.getPrivatekey();
            String kjtPublicKey = kjtConfig.getKjtpublickey();
            SecurityService    securityService = new SecurityService(privateKey, kjtPublicKey);
            Map<String,String> req = JSON.parseObject(signData, HashMap.class);
            String charset = "UTF-8";
            String signType = "RSA";
            if(StringUtils.isNotBlank(charset) && StringUtils.isNotBlank(signType)){
                if("instant_trade".equals(req.get("service"))
                        || "transfer_to_card".equals(req.get("service"))
                        || "trade_close".equals(req.get("service"))
                        || "trade_refund".equals(req.get("service"))
                        || "member_quickly_register".equals(req.get("service"))
                        || "transfer_to_account".equals(req.get("service"))){
                    //使用请求map方式签名-RSA签名
                    return securityService.sign(req, charset);
                }
            }
        }
        return "请求参数不正确！";
    }

    /**
     * 转换即时到账参数
     * @param req
     * @return
     */
    public com.alibaba.fastjson.JSONObject convertInstantTradeParm(String req){
        com.alibaba.fastjson.JSONObject tradeReq = com.alibaba.fastjson.JSONObject.parseObject(req);
        //设置复杂属性
        //转换支付方式pay_method
        String pay_method = tradeReq.getString("pay_method");
        com.alibaba.fastjson.JSONObject payMethod = com.alibaba.fastjson.JSONObject.parseObject(pay_method);
        tradeReq.put("pay_method", payMethod);

        //转换终端信息域terminal_info
        String terminal_info = tradeReq.getString("terminal_info");
        com.alibaba.fastjson.JSONObject terminalInfo = com.alibaba.fastjson.JSONObject.parseObject(terminal_info);
        tradeReq.put("terminal_info", terminalInfo);
        //转换商户自定义域merchant_custom
        String merchant_custom = tradeReq.getString("merchant_custom");
        com.alibaba.fastjson.JSONObject merchantCustom = com.alibaba.fastjson.JSONObject.parseObject(merchant_custom);
        tradeReq.put("merchant_custom", merchantCustom);

        //转换交易信息trade_info
        String trade_info = tradeReq.getString("trade_info");

        if(StringUtils.isNotBlank(trade_info)){
            com.alibaba.fastjson.JSONObject tradeInfo = com.alibaba.fastjson.JSONObject.parseObject(trade_info);

//			//转换交易扩展参数trade_ext
//			String trade_ext = tradeInfo.getString("trade_ext");
//			if(StringUtils.isNoneBlank(trade_ext)){
//				JSONObject tradeExt = JSONObject.parseObject(trade_ext);
//				tradeInfo.put("trade_ext", tradeExt);
//			}

            //转换分账列表royalty_info
            String royalty_info = tradeInfo.getString("royalty_info");
            if(StringUtils.isNotBlank(royalty_info)){
                JSONArray royaltyInfos = JSONArray.parseArray(royalty_info);
                tradeInfo.put("royalty_info", royaltyInfos);
            }
            tradeReq.put("trade_info", tradeInfo);
            return tradeReq;
        }
        return null;
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
        if(org.apache.commons.lang3.StringUtils.isNoneBlank(verifyData)){
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

    public static  JSONObject getPatamterToMap(String str){
        JSONObject jsonObject = new JSONObject();
        String[] paramsArr = str.split("&");

        for(String para : paramsArr){
            String[] tempt = para.split("=");
            jsonObject.put(tempt[0],tempt[1]);
        }
        return jsonObject;
    }
}
