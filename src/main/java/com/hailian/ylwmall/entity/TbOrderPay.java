package com.hailian.ylwmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 19012964
 * @since 2020-05-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbOrderPay extends Model<TbOrderPay> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商户交易订单号
     */
    private String outTradeNo;

    /**
     * 快捷通生成的交易订单号
     */
    private String tradeNo;

    /**
     * 支付状态（0：待支付 1：已支付 2：支付失败）
     */
    private Integer payStatus;

    /**
     * 支付方式（1：银联支付 2：协议支付 3：扫码支付）
     */
    private Integer payType;

    /**
     * 协议号（协议支付使用）
     */
    private String tokenId;

    /**
     * 支付令牌（协议支付使用）
     */
    private String payToken;

    /**
     * 支付提交时间
     */
    private Date payTime;

    /**
     * 支付确认时间
     */
    private Date payconfirmTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
