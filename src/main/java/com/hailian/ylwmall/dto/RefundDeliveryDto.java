package com.hailian.ylwmall.dto;

import lombok.Data;

/**
 * Created by 19012964 on 2020/5/20.
 */
@Data
public class RefundDeliveryDto {
    /**
     * 退货ID
     */
    private Long refundId;

    /**
     * 快递公司编码
     */
    private String expressCode;

    /**
     * 快递公司
     */
    private String expressCompany;

    /**
     * 快递单号
     */
    private String expressId;

}
