package com.hailian.ylwmall.dto;

import lombok.Data;

/**
 * Created by 19012964 on 2020/5/2.
 */
@Data
public class BuyFormDto {
    /**
     * 商品ID
     */
    private Long goodsId;
    /**
     * 商品规格
     */
    private String goodsAttr;
    /**
     * 销量
     */
    private Integer goodsNum;

}
