package com.hailian.ylwmall.dto;

import lombok.Data;

@Data
public class ModuleDetailAddRes {
    private Long id;

    /**
     * 专区ID
     */
    private Long modId;

    /**
     * 产品ID
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品首图地址
     */
    private String imgUrl;

    /**
     * 调整地址
     */
    private String jumpUrl;

    /**
     * 是否是最左边推荐位：0 否 1 是
     */
    private String isHead;

    /**
     * 排序值(字段越大越靠前)
     */
    private Integer modRank;
}
