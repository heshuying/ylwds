package com.hailian.ylwmall.dto;

import lombok.Data;

/**
 * Created by 19012964 on 2020/5/2.
 */
@Data
public class GoodsSimpleDto {
    private Long goodsId;
    private String goodsName;

    private String  goodsIntro;

    private String  goodsCarousel;

    /**
     * 平台利润
     */
    private String profit;

    /**
     * 供货价
     */
    private String sellingPrice;

    /**
     * 售价
     */
    private String price;

    /**
     * 原始价格
     */
    private String originalPrice;
}
