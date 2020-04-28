package com.hailian.ylwmall.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by 19012964 on 2020/4/21.
 */
@Data
public class SettleUpdateDto {
    /**
     * 结算Id
     */
    private Long settleId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 发票路径
     */
    private String billImg;
}
