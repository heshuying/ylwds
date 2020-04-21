package ltd.newbee.mall.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 19012964 on 2020/4/20.
 */
@Data
public class UnSettleListDto {
    /**
     * 供应商价格
     */
    private BigDecimal buyingPrice;
    /**
     * 平台减免金额
     */
    private BigDecimal cutDown;
    /**
     * 订单价格
     */
    private BigDecimal price;
    private String goodsName;
    private String userName;
    private String supName;
    private Long userId;
    private Long orderId;
    /**
     * 销量
     */
    private Integer saleNum;
}
