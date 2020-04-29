package com.hailian.ylwmall.entity.order;

import com.hailian.ylwmall.common.Constants;
import lombok.Data;

@Data
public class OrderQueryParam {

    private Long id;

    private Integer status;

    private Integer userId;

    private Integer pageNumber;

    private String systemType;

    private Integer pageSize = Constants.ORDER_SEARCH_PAGE_LIMIT;

}
