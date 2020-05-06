package com.hailian.ylwmall.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by 19012964 on 2020/5/6.
 */
@Data
public class SupplierCartDto {
    /**
     * 供应商
     */
    private Long supplierId;
    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 购物车里面供应商的产品
     */
    private List<ShoppingGoodsDto> list;
}
