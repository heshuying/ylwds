package com.hailian.ylwmall.dto;

import java.io.Serializable;

/**
 * @author zhp.dts
 * @date 2019/1/25.
 */
public class PayQrcodeVo implements Serializable {
    private String orderCode;
    private String tradeNo;
    private String payData;
    private String payAmount;
    private String payType;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getPayData() {
        return payData;
    }

    public void setPayData(String payData) {
        this.payData = payData;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
