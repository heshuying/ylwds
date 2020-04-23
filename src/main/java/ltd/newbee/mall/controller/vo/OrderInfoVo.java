package ltd.newbee.mall.controller.vo;

import lombok.Data;
import ltd.newbee.mall.entity.order.OrderGoodInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderInfoVo implements Serializable {
    private Long id;

    private String status;

    private String createTimeString;

    private String updateTimeString;

    private Date updateTime;

    private Date createTime;

    private String deliveryAddress;

    private String expressCompany;

    private String expressId;

    private String userRemark;

    private Integer customerId;

    private Integer supplierId;

    private List<OrderGoodInfoVo> goods;

    private BigDecimal totalPrice;

    private BigDecimal grossProfit;

    private BigDecimal buyingPrice;

    private BigDecimal cutDown;

    private BigDecimal realPrice;


}
