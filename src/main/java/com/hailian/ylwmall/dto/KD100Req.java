package com.hailian.ylwmall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KD100Req {
    @ApiModelProperty(value="快递公司编码",name="快递公司编码")
    private String com;
    @ApiModelProperty(value="物流编号",name="物流编号")
    private String num;
}
