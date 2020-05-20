package com.hailian.ylwmall.dto;

import lombok.Data;

/**
 * Created by 19012964 on 2020/5/20.
 */
@Data
public class RefundApplyDto {
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 订单产品ID
     */
    private Long orderGoodsId;
    /**
     * 退货数量
     */
    private Integer refundNum;

    /**
     * 退货原因代码
     */
    private String refundReason;

    /**
     * 退货原因描述
     */
    private String refundReasonDesc;

    /**
     * 退货详细原因
     */
    private String refundDetail;

}
