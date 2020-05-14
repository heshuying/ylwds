package com.hailian.ylwmall.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 19012964 on 2020/5/2.
 */
@Data
public class MyOrderRespDto {
    /**
     * 产品列表
     */
    private List<ShoppingGoodsDto> list;
    /**
     * 订单id
     */
    private Long id;

    /**
     * 订单状态
     1-待支付    2-待发货   3-待收货   4-待评价   5-售后中    6-待结算    7-结算中     8-已结算   9-已取消
     */
    private Integer status;

    private String statusDesc;


    /**
     * 下单时间
     */
    private String createDate;

    /**
     * 收货地址
     */
    private String deliveryAddress;

    /**
     * 快递公司
     */
    private String expressCompany;

    /**
     * 快递单号
     */
    private String expressId;

    /**
     * 下单用户的id
     */
    private Long customerId;

    /**
     * 供货商的id
     */
    private Long supplierId;

    private String supplierName;

    private BigDecimal realPrice;

    /**
     * 运费
     */
    private BigDecimal deliveryFee;

    /**
     * 收货地址ID
     */
    private Long deliveryId;
}
