package com.hailian.ylwmall.common.pay;

/**
 * 退款状态
 */
public enum PayRefundStatusEnum {

    REFUND_Q("Q", "请求中"),
    REFUND_P("P", "处理中"),
    REFUND_S("S", "成功"),
    REFUND_F("F", "失败");

    private String code;

    private String name;

    PayRefundStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PayRefundStatusEnum getPayRefundStatusEnumByCode(String code) {
        for (PayRefundStatusEnum payRefundStatusEnum : PayRefundStatusEnum.values()) {
            if (payRefundStatusEnum.getCode() == code) {
                return payRefundStatusEnum;
            }
        }
        return REFUND_Q;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
