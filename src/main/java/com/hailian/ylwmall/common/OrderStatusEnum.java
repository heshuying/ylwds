package com.hailian.ylwmall.common;

/**
 * 订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭
 */
public enum OrderStatusEnum {

    DEFAULT(-9, "ERROR"),

    ORDER_PRE_PAY(1, "待支付"),
    OREDER_PRE_OUT(2, "待发货"),
    OREDER_PRE_IN(3, "待收货"),
    OREDER_PRE_EVALUATE(4, "待评价"),
    ORDER_CUSTOMER_SERVICE(5, "售后中"),
    ORDER_PRE_SETTLE(6, "待结算"),
    ORDER_IN_SETTLE(7, "结算中"),
    ORDER_SETTLED(8, "已结算"),
    ORDER_CANCEL(9, "已取消");
    private int orderStatus;

    private String name;

    OrderStatusEnum(int orderStatus, String name) {
        this.orderStatus = orderStatus;
        this.name = name;
    }

    public static OrderStatusEnum getNewBeeMallOrderStatusEnumByStatus(int orderStatus) {
        for (OrderStatusEnum newBeeMallOrderStatusEnum : OrderStatusEnum.values()) {
            if (newBeeMallOrderStatusEnum.getOrderStatus() == orderStatus) {
                return newBeeMallOrderStatusEnum;
            }
        }
        return DEFAULT;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
