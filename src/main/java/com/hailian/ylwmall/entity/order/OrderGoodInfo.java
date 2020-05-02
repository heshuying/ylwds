package com.hailian.ylwmall.entity.order;

public class OrderGoodInfo extends OrderGoodInfoKey {
    private Integer number;
    private String goodsAttr;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getGoodsAttr() {
        return goodsAttr;
    }

    public void setGoodsAttr(String goodsAttr) {
        this.goodsAttr = goodsAttr;
    }
}