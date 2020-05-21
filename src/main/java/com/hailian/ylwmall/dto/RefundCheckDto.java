package com.hailian.ylwmall.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 19012964 on 2020/5/20.
 */
@Data
public class RefundCheckDto {
    /**
     * 退货ID
     */
    private Long orderId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 拒绝原因
     */
    private String rejectReason;
    /**
     * 备注
     */
    private String comment;

    /**
     * 收货地址
     */
    private Long deveryId;

    /**
     * 扣除损失
     */
    private BigDecimal contdownAmount;
}
