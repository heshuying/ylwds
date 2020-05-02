package com.hailian.ylwmall.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by 19012964 on 2020/5/2.
 */
@Data
public class OrderSubmitDto {
    private List<BuyRespDto> list;
    private Long deliveryId;
    private String payType;
}
