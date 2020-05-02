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
    private List<ShoppingGoodsDto> list;
    private BigDecimal total;
    private BigDecimal expressFee;
}
