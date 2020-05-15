package com.hailian.ylwmall.entity.order;

import lombok.Data;

@Data
public class DeliverGoodsParam {
    private String userId;

    private Long orderId;

    private String expressCompany;

    private String expressNumber;
    /**
     * 快递公司编码
     */
    private String expressCode;
}
