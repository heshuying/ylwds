package com.hailian.ylwmall.dto;

import java.io.Serializable;
import java.util.List;

public class CreatePayQrcodeTo implements Serializable {
    private List<String> orderCodeList;
    private String payType;

    public List<String> getOrderCodeList() {
        return orderCodeList;
    }

    public void setOrderCodeList(List<String> orderCodeList) {
        this.orderCodeList = orderCodeList;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
