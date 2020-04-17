package ltd.newbee.mall.dto;

import lombok.Data;

@Data
public class GoodsStatusUpdateReqDTO {
    /**产品id*/
    private Long[] ids;

    /**下架原因*/
    private String msgOffline;

    /**上架驳回原因*/
    private String msgReject;
}
