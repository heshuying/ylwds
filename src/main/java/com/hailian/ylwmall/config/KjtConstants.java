package com.hailian.ylwmall.config;

/**
 * Created by 01440590 on 2019/1/22.
 */
public class KjtConstants {
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
