package com.hailian.ylwmall.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by 19012964 on 2020/4/21.
 */
@Data
public class SettleQueryDto {
    private Integer page;
    private Integer limit;
    private Long supplierId;
    private Date startDate;
    private Date endDate;
    private Integer status;
}
