package ltd.newbee.mall.entity.order;

import lombok.Data;

@Data
public class DeliverGoodsParam {
    private String userId;

    private Long orderId;

    private String expressCompany;

    private String expressNumber;
}
