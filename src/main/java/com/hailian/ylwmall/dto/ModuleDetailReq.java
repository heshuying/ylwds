package com.hailian.ylwmall.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

/**
 * 分区详情
 */
@Data
public class ModuleDetailReq {
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

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(new ModuleDetailReq(), SerializerFeature.WriteMapNullValue));
    }

}
