package com.hailian.ylwmall.common.pay;

import org.apache.commons.lang3.StringUtils;

/**
 * 业务产品码
 * @author 19033323
 */
public enum ProductCodeEnum {

    ENSURE_TRADE_20202("20202", "担保交易"),
    ENSURE_TRADE_20702("20702", "担保交易-先分账后结算");

    private  String code;

    /**说明*/
    private String message;

    ProductCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }



    public static ProductCodeEnum getByCode(String code)
    {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (ProductCodeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
