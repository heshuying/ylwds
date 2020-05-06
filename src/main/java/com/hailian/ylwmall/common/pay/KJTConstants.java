package com.hailian.ylwmall.common.pay;

public class KJTConstants {
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String DEFAULT_VERSION = "1.0";
    public static final String DEFAULT_FORMAT = "JSON";
    public static final String SIGN_TYPE_ITRUSSRV = "ITRUS";
    public static final String SIGN_TYPE_RSA = "RSA";
    public static final String SIGN_TYPE_MD5 = "MD5";
    public static final String NOTIFY_RET_SUCCESS = "success";

    /**服务名*/
    public static final String SERVICE_ENSURE_TRADE = "ensure_trade"; // 担保交易
    public static final String SERVICE_TRADE_SETTLE = "trade_settle"; // 交易达成
    public static final String SERVICE_AGREEMENT_PAY_CONFIRM = "agreement_pay_confirm"; // 协议支付/ 直接支付-支付确认

    public static class PayerIdentityType {//快捷通会员标识类型
        public static final String PAYER_IDENTITY_TYPE_1 = "1";//快捷通会员id
        public static final String PAYER_IDENTITY_TYPE_2 = "2";//快捷通会员登录号
    }

    public static class CurrencyType {//分账标识类型，默认1
        public static final String CNY = "CNY";//快捷通会员id
    }


    public static class InstantTrade {//及时到账业务参数
        public static class BizContent {//条码-主扫
            public static final String PAYER_IDENTITY_ID = "anonymous";//卖家会员id或登录账号，没有则固定anonymous
            public static final String PAYER_PLATFORM_TYPE = "1";//买家平台类型


            public static class BizProductCode {//业务产品码
                public static final String BIZ_PRODUCT_CODE_20601 = "20601";//即时到账-电商
                public static final String BIZ_PRODUCT_CODE_20401 = "20401";//即时到账-互金
                public static final String BIZ_PRODUCT_CODE_20602 = "20602";//线下收单
                public static final String BIZ_PRODUCT_CODE_20701 = "20701";//收单（先分账后结算）
            }

            public static class CashierType {//收银台类型
                public static final String WEB = "WEB";
                public static final String SDK = "SDK";
                public static final String H5 = "H5";
                public static final String API = "API";
            }
        }
    }
}
