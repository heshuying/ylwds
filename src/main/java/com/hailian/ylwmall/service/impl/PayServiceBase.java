package com.hailian.ylwmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hailian.ylwmall.common.pay.CertificatesTypeEnum;
import com.hailian.ylwmall.common.pay.KJTConstants;
import com.hailian.ylwmall.config.KjtConfig;
import com.hailian.ylwmall.dto.pay.CardRegisterApplyAndPayBean;
import com.hailian.ylwmall.exception.BusinessException;
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

        return requestBase;
    }

    /**
     * 支付方式构建
     * @return
     */
    protected Map<String, String> getPayMap(String payType, BigDecimal price){
        // 支付方式设置
        if("1".equals(payType)){
            Map<String,String> payMethod = new HashMap<>();
            payMethod.put("pay_product_code", "20");
            payMethod.put("amount", price.toString());
            payMethod.put("bank_code", "CCB");
            return payMethod;
        }else if("2".equals(payType)){
            CardRegisterApplyAndPayBean payBean = new CardRegisterApplyAndPayBean();
            payBean.setPayProductCode("51");
            payBean.setAmount(price.toString());
            payBean.setSigningPay("Y");
            payBean.setBankCardNo("6217002390010212212");
            payBean.setPhoneNum("15610026960");
            payBean.setBankAccountName("何树营");
            payBean.setCertificatesType(CertificatesTypeEnum.ID_CARD.getCode());
            payBean.setCertificatesNumber("230722198504031210");
            return KJTPayUtil.objToMap(payBean);
        }

        return null;

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
}
