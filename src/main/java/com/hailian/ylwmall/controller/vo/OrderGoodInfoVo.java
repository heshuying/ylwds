package com.hailian.ylwmall.controller.vo;

import com.hailian.ylwmall.entity.order.OrderGoodInfo;
import lombok.Data;

@Data
public class OrderGoodInfoVo extends OrderGoodInfo {
    private String goodName;

    private double unitPrice;

    @Override
    public String toString() {
        return goodName+"*"+getNumber();
    }
}
