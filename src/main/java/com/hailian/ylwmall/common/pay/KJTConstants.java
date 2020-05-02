package com.hailian.ylwmall.common.pay;

public class KJTConstants {
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String DEFAULT_VERSION = "1.0";
    public static final String DEFAULT_FORMAT = "JSON";
    public static final String SIGN_TYPE_ITRUSSRV = "ITRUS";
    public static final String SIGN_TYPE_RSA = "RSA";
    public static final String SIGN_TYPE_MD5 = "MD5";

    /**服务名*/
    public static final String SERVICE_ENSURE_TRADE = "ensure_trade"; // 担保交易
    public static final String SERVICE_TRADE_SETTLE = "trade_settle"; // 交易达成
    public static final String SERVICE_AGREEMENT_PAY_CONFIRM = "agreement_pay_confirm"; // 协议支付/ 直接支付-支付确认
}
