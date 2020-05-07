package com.hailian.ylwmall.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 19012964 on 2020/5/2.
 */
@Data
public class OrderDetailDto {
    /**
     * 订单产品列表
     */
    private List<ShoppingGoodsDto> list;
    /**
     * 总价
     */
    private BigDecimal total;
    /**
     * 运费
     */
    private BigDecimal expressFee;

    private String payType;

    private Long orderNo;

}
