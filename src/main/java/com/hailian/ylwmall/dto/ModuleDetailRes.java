package com.hailian.ylwmall.dto;

import com.hailian.ylwmall.entity.TbModuleDetail;
import lombok.Data;

@Data
public class ModuleDetailRes extends TbModuleDetail {

    private String goodsName;

    private String  goodsIntro;

    /**
     * 平台利润
     */
    private String profit;

    /**
     * 供货价
     */
    private String sellingPrice;

    /**
     * 售价
     */
    private String price;

    /**
     * 原始价格
     */
    private String originalPrice;

}
