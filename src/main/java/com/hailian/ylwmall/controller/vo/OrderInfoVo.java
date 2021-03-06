package com.hailian.ylwmall.controller.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderInfoVo implements Serializable {
    private Long id;

    private Integer status;
    private String statusDesc;
    private String createTimeString;

    private String updateTimeString;

    private Date updateTime;

    private Date createTime;

    private String deliveryAddress;

    private String expressCompany;

    private String expressId;
    private String expressCode;
    private String userRemark;

    private Long customerId;

    private String customerName;

    private String supplierName;

    private Long supplierId;

    private List<OrderGoodInfoVo> goods;

    private BigDecimal totalPrice;

    private BigDecimal grossProfit;

    private BigDecimal buyingPrice;

    private BigDecimal cutDown;

    private BigDecimal realPrice;


}
