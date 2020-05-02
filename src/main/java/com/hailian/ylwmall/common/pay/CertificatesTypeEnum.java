package com.hailian.ylwmall.common.pay;

import org.apache.commons.lang3.StringUtils;

/**
 * 证件类型
 * @author 19033323
 */
public enum CertificatesTypeEnum {
    ID_CARD("01","身份证"),
    SOLDIER("02","军官证"),
    PASSPORT("02","护照"),
    ;
    private  String code;

    /**说明*/
    private String message;

    CertificatesTypeEnum(String code, String message) {
      
        this.code = code;
        this.message = message;
    }



    public static CertificatesTypeEnum getByCode(String code)
    {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (CertificatesTypeEnum type : values()) {
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
