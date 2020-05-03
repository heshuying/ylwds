package com.hailian.ylwmall.dto;

import com.hailian.ylwmall.entity.TbShoppingCart;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 19012964 on 2020/5/2.
 */
@Data
public class ShoppingGoodsDto {
    private Long id;

    /**
     * 用户主键id
     */
    private Long userId;

    /**
     * 关联商品id
     */
    private Long goodsId;
    private Long supplierId;
    /**
     * 规格
     */
    private String goodsAttr;
    /**
     * 数量
     */
    private Integer goodsCount;

   private String goodsName;

    private String goodsCoverImg;

    /**原价*/
    private BigDecimal originalPrice;
    /**供货价*/
    private BigDecimal sellingPrice;
    /**平台利润*/
    private BigDecimal profit;
    /**售价*/
    private BigDecimal price;
    /**
     * 运费
     */
    private BigDecimal transitMoney;
}
