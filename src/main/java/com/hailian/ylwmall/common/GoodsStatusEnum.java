package com.hailian.ylwmall.common;

/**
 * 商品状态
 * @author 19033323
 */
public enum GoodsStatusEnum {

    INSTORE(1, "仓库中"),
    AUDITTING(2, "审核中待上架"),
    SELLING(3, "销售中"),
    SELLING_OFF_REQUEST(4, "销售中申请下架"),
    SELLING_OFF_FRONT(5, "销售中申请下架已前端下架"),
    OFF_INSTORE(6, "已下架仓库中");


    private Integer goodsStatus;
    private String name;

    GoodsStatusEnum(Integer goodsStatus, String name){
        this.goodsStatus = goodsStatus;
        this.name = name;
    }

    public Integer getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(int goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
