package ltd.newbee.mall.entity.order;

import lombok.Data;
import ltd.newbee.mall.common.Constants;

@Data
public class OrderQueryParam {

    private Long id;

    private Integer status;

    private Integer userId;

    private Integer pageNumber;

    private String systemType;

    private Integer pageSize = Constants.ORDER_SEARCH_PAGE_LIMIT;

}
