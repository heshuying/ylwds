package com.hailian.ylwmall.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by 01440590 on 2019/1/27.
 */
@Component
@ConfigurationProperties(prefix = "kjt")
@Data
public class KjtConfig {
    private String payeeidentity;//商户id
    private String privatekey;//商户私钥
    private String kjtpublickey;//快捷通公钥
    private String recv;
    private String instantTradeAsyncNotify;//付款回写接口
    private String transferToCardAsyncNotify;//体现回写接口
    private String tradeRefundAsyncNotify;//退款回写接口
    private String transferToAccountAsyncNotify;//转账到户回写接口
    private String ensureTradeAsyncNotify; //担保支付异步通知接口
    private String ensureTradeReturnUrl; //担保支付完成跳转页面地址
    private String recvOrder;//快捷通个人账号快速注册接口
    private String verifyFlag;//1，校验；0，不校验
    private String productCode;//业务产品码 10310-会员转账（实时） 10311-会员转账（普通） 10312-会员转账（次日）
}
