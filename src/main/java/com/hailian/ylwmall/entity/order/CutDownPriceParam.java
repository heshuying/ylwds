package com.hailian.ylwmall.entity.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CutDownPriceParam {

    private Long orderId;

    private BigDecimal cutdownNumber;

    private Integer userId;
}
