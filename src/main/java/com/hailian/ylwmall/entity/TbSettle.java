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
 * 结算表
 * </p>
 *
 * @author 19012964
 * @since 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbSettle extends Model<TbSettle> {

    private static final long serialVersionUID=1L;

    /**
     * 结算表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long supplierId;

    /**
     * 订单状态：01-待确认；02-待平台更改；03-待开票   04-待付款   05-已完结
     */
    private Integer status;

    /**
     * 结算单号
     */
    private String settleNo;

    /**
     * 结算单名称
     */
    private String settleName;

    /**
     * 结算开始时间
     */
    private String beginPeriod;

    /**
     * 结算开始时间
     */
    private String endPeriod;

    /**
     * 结算金额
     */
    private BigDecimal amount;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
