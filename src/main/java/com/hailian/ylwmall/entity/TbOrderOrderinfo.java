package com.hailian.ylwmall.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单表（主）
 * </p>
 *
 * @author 19012964
 * @since 2020-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbOrderOrderinfo extends Model<TbOrderOrderinfo> {

    private static final long serialVersionUID=1L;

    /**
     * 订单id
     */
    private Long id;

    /**
     * 订单状态
1-待支付    2-待发货   3-待收货   4-待评价   5-售后中    6-待结算    7-结算中     8-已结算   9-已取消
     */
    private Integer status;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 下单时间
     */
    private Date createTime;

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
     * 用户备注
     */
    private String userRemark;

    /**
     * 下单用户的id
     */
    private Long customerId;

    /**
     * 供货商的id
     */
    private Long supplierId;

    /**
     * 平台减免金额
     */
    private BigDecimal cutDown;

    /**
     * 订单原售价 页面划线价
     */
    private BigDecimal totalPrice;

    /**
     * 订单毛利润 =平台利润-平台减免
     */
    private BigDecimal grossProfit;

    /**
     * 订单进货价=供应商价格
     */
    private BigDecimal buyingPrice;

    /**
     * 订单最终售价=供应商价格+平台利润+运费-平台减免
     */
    private BigDecimal realPrice;

    /**
     * 运费
     */
    private BigDecimal deliveryFee;

    /**
     * 收货地址ID
     */
    private Long deliveryId;

    private String payType;

    /**
     * 快递公司编码
     */
    private String expressCode;
    private String cutdownImg;
    private Date confirmDate;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
