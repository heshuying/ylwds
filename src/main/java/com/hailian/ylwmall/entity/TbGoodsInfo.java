package com.hailian.ylwmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class TbGoodsInfo {
    /**
     * 用户主键id
     */
    @TableId(value = "goods_id", type = IdType.AUTO)
    private Long goodsId;

    @TableField("user_id")
    private Long userId;

    private String goodsName;

    private String goodsIntro;

    private Long goodsCategoryId;

    private String goodsCoverImg;

    private String goodsCarousel;

    /**原价*/
    private BigDecimal originalPrice;
    /**供货价*/
    private BigDecimal sellingPrice;
    /**平台利润*/
    private BigDecimal profit;
    /**售价*/
    private BigDecimal price;

    private Integer stockNum;

    private String tag;
    private String createSno;
    private String goodsSno;
    /**
     * 商品状态：1 仓库中 2 审核中待上架 3 销售中 4 销售中申请下架 5 销售中申请下架已前端下架 6 已下架仓库中
     */
    private Integer goodsSellStatus;

    private Long createUser;
    /**
     * 运费
     */
    private BigDecimal transitMoney;
    /**
     * 销量
     */
    private Integer saleTotal;
    /**
     * 可选规格：多个以逗号分隔
     */
    private String goodsAttribute;
    /**
     * 下架原因
     */
    private String msgOffline;

    /**
     * 上架驳回原因
     */
    private String msgReject;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Long updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date onlineTime;

    private String goodsDetailContent;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsIntro() {
        return goodsIntro;
    }

    public void setGoodsIntro(String goodsIntro) {
        this.goodsIntro = goodsIntro == null ? null : goodsIntro.trim();
    }

    public Long getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(Long goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public String getGoodsCoverImg() {
        return goodsCoverImg;
    }

    public void setGoodsCoverImg(String goodsCoverImg) {
        this.goodsCoverImg = goodsCoverImg == null ? null : goodsCoverImg.trim();
    }

    public String getGoodsCarousel() {
        return goodsCarousel;
    }

    public void setGoodsCarousel(String goodsCarousel) {
        this.goodsCarousel = goodsCarousel == null ? null : goodsCarousel.trim();
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getGoodsDetailContent() {
        return goodsDetailContent;
    }

    public void setGoodsDetailContent(String goodsDetailContent) {
        this.goodsDetailContent = goodsDetailContent == null ? null : goodsDetailContent.trim();
    }

    public String getCreateSno() {
        return createSno;
    }

    public void setCreateSno(String createSno) {
        this.createSno = createSno;
    }

    public String getGoodsSno() {
        return goodsSno;
    }

    public void setGoodsSno(String goodsSno) {
        this.goodsSno = goodsSno;
    }

    public void setGoodsSellStatus(Integer goodsSellStatus) {
        this.goodsSellStatus = goodsSellStatus;
    }

    public BigDecimal getTransitMoney() {
        return transitMoney;
    }

    public void setTransitMoney(BigDecimal transitMoney) {
        this.transitMoney = transitMoney;
    }

    public Integer getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(Integer saleTotal) {
        this.saleTotal = saleTotal;
    }

    public String getGoodsAttribute() {
        return goodsAttribute;
    }

    public void setGoodsAttribute(String goodsAttribute) {
        this.goodsAttribute = goodsAttribute;
    }

    public String getMsgOffline() {
        return msgOffline;
    }

    public void setMsgOffline(String msgOffline) {
        this.msgOffline = msgOffline;
    }

    public String getMsgReject() {
        return msgReject;
    }

    public void setMsgReject(String msgReject) {
        this.msgReject = msgReject;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Integer getGoodsSellStatus() {
        return goodsSellStatus;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}