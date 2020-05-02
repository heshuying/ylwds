package com.hailian.ylwmall.dto.pay;

import lombok.Data;

/**
 * 担保支付请求参数Bean
 */
@Data
public class EnsureTradeReq {
    // 订单id
    String orderId;
    // 支付方式（1：银联支付 2：协议支付 3：扫码支付）
    String payType;

    // 证件类型
    private String certificatesType;
    // 证件号码
    private String certificatesNumber;
    // 账户名
    private String bankAccountName;
    // 银行卡号
    private String bankCardNo;
    // 安全码，信用卡必传
    private String cvv2;
    // 信用卡有效期 YYYY/MM
    private String validDate;
    // 手机号
    private String phoneNum;

}
