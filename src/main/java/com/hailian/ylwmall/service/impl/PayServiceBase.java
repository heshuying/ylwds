package com.hailian.ylwmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.hailian.ylwmall.common.B2BMallException;
import com.hailian.ylwmall.common.pay.CertificatesTypeEnum;
import com.hailian.ylwmall.common.pay.KJTConstants;
import com.hailian.ylwmall.config.KjtConfig;
import com.hailian.ylwmall.dao.*;
import com.hailian.ylwmall.dto.pay.CardRegisterApplyAndPayBean;
import com.hailian.ylwmall.dto.pay.EnsureTradeBean;
import com.hailian.ylwmall.dto.pay.EnsureTradeReq;
import com.hailian.ylwmall.entity.TbPayRevlog;
import com.hailian.ylwmall.entity.TbPaySenlog;
import com.hailian.ylwmall.entity.TbUserPay;
import com.hailian.ylwmall.exception.BusinessException;
import com.hailian.ylwmall.util.CommonUtil;
import com.hailian.ylwmall.util.KJTPayUtil;
import com.hailian.ylwmall.util.RestUtils;
import com.kjtpay.gateway.common.constant.ReqValue;
import com.kjtpay.gateway.common.domain.VerifyResult;
import com.kjtpay.gateway.common.domain.base.RequestBase;
import com.kjtpay.gateway.common.domain.base.ResponseParameter;
import com.kjtpay.gateway.common.util.security.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付父类
 * @author 19033323
 */
@Slf4j
public class PayServiceBase {
    @Autowired
    KjtConfig kjtConfig;
    @Autowired
    SecurityService securityService;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    TbOrderPayDao orderPayDao;
    @Autowired
    OrderGoodInfoMapper orderGoodInfoMapper;
    @Autowired
    TbUserPayDao userPayDao;
    @Autowired
    TbPaySenlogDao paySenlogDao;
    @Autowired
    TbPayRevlogDao payRevlogDao;

    Gson gson = new Gson();

    /**
     * 最终请求参数构建及加密和签名
     * @param bizContent
     * @param requestNo
     * @return
     */
    public RequestBase genRequestBase(String bizContent, String requestNo, String service){
        // 构建公共请求对象
        RequestBase requestBase = new RequestBase();
        requestBase.setService(service);
        requestBase.setVersion(KJTConstants.DEFAULT_VERSION);
        requestBase.setCharset(KJTConstants.DEFAULT_CHARSET);
        requestBase.setFormat(KJTConstants.DEFAULT_FORMAT);
        requestBase.setSignType(KJTConstants.SIGN_TYPE_RSA);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        requestBase.setTimestamp(simpleDateFormat.format(new Date()));
        requestBase.setPartnerId(kjtConfig.getPayeeidentity());
        requestBase.setRequestNo(requestNo);

        // RSA加密
        log.info("加密前bizContent： {}", bizContent);
        String bizContentEncryptStr = securityService.encrypt(bizContent, KJTConstants.DEFAULT_CHARSET);
        log.info("加密后bizContent： {}", bizContentEncryptStr);
        requestBase.setBizContent(bizContentEncryptStr);

        // 签名
        String signDataStr = securityService.sign(requestBase, ReqValue.DEFAULT_CHARSET);
        log.info("签名sign：{}", signDataStr);
        requestBase.setSign(signDataStr);
        log.info("最终请求数据requestBase： {}", gson.toJson(requestBase));

        insertSendLog(requestNo, service, bizContent);
        return requestBase;
    }

    /**
     * 支付方式构建
     * @return
     */
    protected EnsureTradeBean setPay(EnsureTradeBean tradeBean, EnsureTradeReq reqBean, Long userId, BigDecimal price){
        // 支付方式设置
        if("1".equals(reqBean.getPayType())){
//            Map<String,String> payMethod = new HashMap<>();
//            payMethod.put("pay_product_code", "20");
//            payMethod.put("amount", price.toString());
//            payMethod.put("bank_code", "CCB");
            // 直接跳转到网银选择页面
            Map<String,String> customMap = new HashMap<>();
            customMap.put("go_cashier", "Y");
            customMap.put("cashier_login", "N");
            customMap.put("need_login", "N");
            tradeBean.setMerchantCustom(customMap);
            tradeBean.setPayMethod(null);
            return tradeBean;
        }else if("2".equals(reqBean.getPayType())){
            TbUserPay userPay = userPayDao.selectOne(new QueryWrapper<TbUserPay>().eq("user_id", userId));
            if(userPay == null){
                if(StringUtils.isBlank(reqBean.getBankCardNo())
                        || StringUtils.isBlank(reqBean.getBankAccountName())
                        || StringUtils.isBlank(reqBean.getCertificatesType())
                        || StringUtils.isBlank(reqBean.getCertificatesNumber())
                        || StringUtils.isBlank(reqBean.getPhoneNum())){
                    B2BMallException.fail("协议支付银行卡信息不全");
                }
                userPay = CommonUtil.convertBean(reqBean, TbUserPay.class);
                userPay.setUserId(userId);
                userPay.setTokenIsvalid(0);
                userPay.setCreateTime(new Date());
                userPay.setUpdateTime(new Date());
                userPayDao.insert(userPay);
            }

            CardRegisterApplyAndPayBean payBean = new CardRegisterApplyAndPayBean();
            payBean.setPayProductCode("51");
            payBean.setAmount(price.toString());
            if(!StringUtils.isBlank(userPay.getTokenId())  && userPay.getTokenIsvalid() == 1){
                payBean.setSigningPay("N");
                payBean.setTokenId(userPay.getTokenId());
            }else{
                if(StringUtils.isBlank(reqBean.getBankCardNo())
                        || StringUtils.isBlank(reqBean.getBankAccountName())
                        || StringUtils.isBlank(reqBean.getCertificatesType())
                        || StringUtils.isBlank(reqBean.getCertificatesNumber())
                        || StringUtils.isBlank(reqBean.getPhoneNum())){
                    B2BMallException.fail("协议支付银行卡信息不全");
                }
                userPay = CommonUtil.convertBean(reqBean, TbUserPay.class);
                userPay.setUserId(userId);
                userPay.setTokenIsvalid(0);
                userPay.setUpdateTime(new Date());
                userPayDao.updateById(userPay);

                payBean.setSigningPay("Y");
                payBean.setBankCardNo(reqBean.getBankCardNo());
                payBean.setPhoneNum(reqBean.getPhoneNum());
                payBean.setBankAccountName(reqBean.getBankAccountName());
                payBean.setCertificatesType(CertificatesTypeEnum.ID_CARD.getCode());
                payBean.setCertificatesNumber(reqBean.getCertificatesNumber());

            }
            tradeBean.setPayMethod(KJTPayUtil.objToMap(payBean));
            return tradeBean;
        }else if("3".equals(reqBean.getPayType())){
            Map<String,String> payMethod = new HashMap<>();
            payMethod.put("pay_product_code", "83");
            payMethod.put("amount", price.toString());
            payMethod.put("token_valid_minutes", "10");
            tradeBean.setPayMethod(payMethod);
            return tradeBean;
        }

        return tradeBean;

    }

    public ResponseParameter callKjt(RequestBase requestBase){
        // 请求快捷通接口
        net.sf.json.JSONObject jsonObjectFinal = KJTPayUtil.getJsonObjectFinal(requestBase);
        String resultKjt = RestUtils.httpPostWithJson(jsonObjectFinal, kjtConfig.getRecv());
        log.info("快捷通反回ResponseParameter：" + resultKjt);

        // 返回结果验签
        ResponseParameter rp = new ResponseParameter();
        VerifyResult verifyResult;
        if(StringUtils.isNotBlank(resultKjt)){
            rp = gson.fromJson(resultKjt, rp.getClass());
            String bizContent = rp.getBizContent()==null ? null : JSON.toJSONString(rp.getBizContent());
            rp.setBizContent(bizContent);
            // 记录返回日志
            insertRevLog(requestBase.getRequestNo(), requestBase.getService(), gson.toJson(rp.getBizContent()), resultKjt);

            String signType = rp.getSignType();
            String charset = rp.getCharset();
            String sign = rp.getSign();
            if("RSA".equals(signType)){
                //RSA验签
                verifyResult = securityService.verify(rp, sign, charset);
            }else {
                throw new BusinessException("不支持的加密类型：" + signType);
            }

            if(!verifyResult.isSuccess()){
                log.error("验签失败biz_content： " + bizContent);
            }else if(verifyResult.isSuccess()){
                log.info("验签成功");
            }
        }else{
            throw new BusinessException("快捷通接口调用失败，返回null");
        }
        return rp;
    }

    public int insertSendLog(String outTradeNo, String serviceName, String bizContent){
        TbPaySenlog senlog = new TbPaySenlog();
        senlog.setOutTradeNo(outTradeNo);
        senlog.setServiceName(serviceName);
        senlog.setBizContent(bizContent);
        senlog.setStatusCall(0);
        senlog.setCreateTime(new Date());
        senlog.setUpdateTime(new Date());
        return paySenlogDao.insert(senlog);
    }

    public int insertRevLog(String outTradeNo, String serviceName, String bizContent, String revJson){
        TbPayRevlog revlog = new TbPayRevlog();
        revlog.setOutTradeNo(outTradeNo);
        revlog.setServiceName(serviceName);
        revlog.setBizContent(bizContent);
        revlog.setRevJson(revJson);
        revlog.setStatusCall(0);
        revlog.setCreateTime(new Date());
        revlog.setUpdateTime(new Date());
        return payRevlogDao.insert(revlog);
    }
}
