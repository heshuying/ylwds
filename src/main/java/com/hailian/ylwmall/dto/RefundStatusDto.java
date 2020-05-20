package com.hailian.ylwmall.dto;

import lombok.Data;

/**
 * Created by 19012964 on 2020/5/20.
 */
@Data
public class RefundStatusDto {
    /**
     * 退货ID
     */
    private Long refundId;

    /**
     * 状态
     */
    private Integer status;

}
