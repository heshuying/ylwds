package com.hailian.ylwmall.dto;

import com.hailian.ylwmall.entity.TbShoppingCart;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 19012964 on 2020/5/2.
 */
@Data
public class BuyRespDto {
    /**
     * 按供应商区分 购物车产品
     */
    private List<SupplierCartDto> list;
    /**
     * 总价
     */
    private BigDecimal total;
    /**
     * 运费
     */
    private BigDecimal expressFee;
}
