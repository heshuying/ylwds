package com.hailian.ylwmall.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 19012964
 * @since 2020-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbOrderRefund extends Model<TbOrderRefund> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品订单表Id
     */
    private Long orderGoodsId;

    /**
     * 规格
     */
    private String goodsAttribute;

    /**
     * 退货数量
     */
    private Integer refundNum;

    /**
     * 退货原因代码
     */
    private String refundReason;

    /**
     * 退货原因描述
     */
    private String refundReasonDesc;

    /**
     * 退货详细原因
     */
    private String refundDetail;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 备注
     */
    private String openComment;

    /**
     * 退货金额
     */
    private BigDecimal refundAmount;

    /**
     * 实际退货金额
     */
    private BigDecimal refundActualAmount;

    /**
     * 发货地址ID
     */
    private Long deliveryId;
    @TableField(exist = false)
    private String deliveryAddr;
    /**
     * 快递公司编码
     */
    private String expressCode;

    /**
     * 快递公司
     */
    private String expressCompany;

    /**
     * 快递单号
     */
    private String expressId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    @TableField(exist = false)
    private Integer status;
    @TableField(exist = false)
    private String statusDesc;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
