package com.hailian.ylwmall.common.pay;

/**
 * 支付状态
 */
public enum PayStatusEnum {

    PAY_FAIL(-1, "支付失败"),
    PAY_ING(0, "支付中"),
    PAY_WAIT(1, "待付款"),
    PAY_WAIT_CONFIRM(2, "待达成"),
    PAY_SUCCESS(3, "支付成功");

    private int payStatus;

    private String name;

    PayStatusEnum(int payStatus, String name) {
        this.payStatus = payStatus;
        this.name = name;
    }

    public static PayStatusEnum getPayStatusEnumByStatus(int payStatus) {
        for (PayStatusEnum payStatusEnum : PayStatusEnum.values()) {
            if (payStatusEnum.getPayStatus() == payStatus) {
                return payStatusEnum;
            }
        }
        return PAY_ING;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
