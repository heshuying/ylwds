package com.hailian.ylwmall.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 19012964
 * @since 2020-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbOrderGoodinfo extends Model<TbOrderGoodinfo> {

    private static final long serialVersionUID=1L;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品id
     */
    private Long goodId;

    /**
     * 下单商品数量
     */
    private Integer number;

    /**
     * 规格
     */
    private String goodsAttr;


    @Override
    protected Serializable pkVal() {
        return this.orderId;
    }

}
