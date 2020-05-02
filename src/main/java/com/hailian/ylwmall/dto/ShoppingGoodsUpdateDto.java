package com.hailian.ylwmall.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 19012964 on 2020/5/2.
 */
@Data
public class ShoppingGoodsUpdateDto {
    private Long id;

    /**
     * 数量(最大为5)
     */
    private Integer goodsCount;

}
