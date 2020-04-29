package com.hailian.ylwmall.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 结算明细表
 * </p>
 *
 * @author 19012964
 * @since 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbSettleDetail extends Model<TbSettleDetail> {

    private static final long serialVersionUID=1L;

    /**
     * 结算明细表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 结算Id
     */
    private Long settleId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 结算单价
     */
    private BigDecimal settlePrice;

    /**
     * 结算数量
     */
    private Integer settleNum;

    /**
     * 结算金额
     */
    private BigDecimal amount;

    /**
     * 创建时间
     */
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
