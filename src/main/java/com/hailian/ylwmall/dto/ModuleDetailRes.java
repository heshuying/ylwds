package com.hailian.ylwmall.dto;

import com.hailian.ylwmall.entity.TbModuleDetail;
import lombok.Data;

@Data
public class ModuleDetailRes extends TbModuleDetail {

    private String goodsName;

    /**
     * 平台利润
     */
    private String profit;

    /**
     * 供货价
     */
    private String sellingPrice;

}
