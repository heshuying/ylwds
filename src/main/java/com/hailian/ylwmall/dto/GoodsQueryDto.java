package com.hailian.ylwmall.dto;

import com.hailian.ylwmall.dto.BuyFormDto;
import lombok.Data;

import java.util.List;

/**
 * Created by 19012964 on 2020/5/2.
 */
@Data
public class GoodsQueryDto {
    private Long moduleId;
    private Long category;
    private String goodsName;
    private String orderby;
}
