package com.hailian.ylwmall.dto;

import lombok.Data;

/**
 * Created by 19012964 on 2020/5/2.
 */
@Data
public class BuyFormDto {
    private Long goodsId;
    private Long userId;

    private String goodsAttr;
    private Integer goodsNum;

}
