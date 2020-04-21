package ltd.newbee.mall.controller.vo;

import lombok.Data;
import ltd.newbee.mall.entity.order.OrderGoodInfo;
@Data
public class OrderGoodInfoVo extends OrderGoodInfo {
    private String goodName;

    private double unitPrice;

    @Override
    public String toString() {
        return goodName+"*"+getNumber();
    }
}
