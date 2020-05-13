package com.hailian.ylwmall.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

@Data
public class CommentReq {
    /**
     * 商品id
     */
    private Long goodsId;

    private Long orderId;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评分：1 2 3 4 5
     */
    private Integer score;

    /**
     * 规格
     */
    private String goodsAttribute;

    /**
     * 图片地址,逗号分隔
     */
    private String picUrl;

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(new CommentReq(), SerializerFeature.WriteMapNullValue));
    }
}
