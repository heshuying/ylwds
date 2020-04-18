package ltd.newbee.mall.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ltd.newbee.mall.common.GoodsStatusEnum;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsAndCompayResDTO {
    private Long goodsId;

    private Long userId;
    private String goodsName;

    private String goodsIntro;

    private Long goodsCategoryId;

    private String goodsCoverImg;

    private String goodsCarousel;

    private BigDecimal originalPrice;
    private BigDecimal sellingPrice;
    private BigDecimal profit;
    private BigDecimal price;
    private Integer stockNum;

    private String tag;
    private String createSno;
    private String goodsSno;
    /**
     * 商品状态：1 仓库中 2 审核中待上架 3 销售中 4 销售中申请下架 5 销售中申请下架已前端下架 6 已下架仓库中
     */
    private Integer goodsSellStatus;

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
    private String gooodsAttribute;
    /**
     * 下架原因
     */
    private String msgOffline;

    /**
     * 上架驳回原因
     */
    private String msgReject;

    /**
     * 上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date onlineTime;

    private String goodsDetailContent;

    private String companyName;

    /**
     * 商品状态显示（平台方）
     * @return
     */
    public String getGoodsStatusPT(){
        if(this.getGoodsSellStatus() == null){
            return "";
        }

        if(GoodsStatusEnum.INSTORE.getGoodsStatus().equals(this.getGoodsSellStatus())){
            return "/";
        }
        if(GoodsStatusEnum.AUDITTING.getGoodsStatus().equals(this.getGoodsSellStatus())){
            return "待上架";
        }
        if(GoodsStatusEnum.SELLING.getGoodsStatus().equals(this.getGoodsSellStatus())){
            return "销售中";
        }
        if(GoodsStatusEnum.SELLING_OFF_REQUEST.getGoodsStatus().equals(this.getGoodsSellStatus())){
            return "待下架";
        }
        if(GoodsStatusEnum.SELLING_OFF_FRONT.equals(this.getGoodsSellStatus())){
            return "前端下架";
        }
        if(GoodsStatusEnum.OFF_INSTORE.equals(this.getGoodsSellStatus())){
            return "已下架";
        }

        return "";
    }

    /**
     * 商品状态显示（资源方）
     * @return
     */
    public String getGoodsStatusZYF(){
        if(this.getGoodsSellStatus() == null){
            return "";
        }

        if(GoodsStatusEnum.INSTORE.getGoodsStatus().equals(this.getGoodsSellStatus()) || GoodsStatusEnum.OFF_INSTORE.equals(this.getGoodsSellStatus())){
            return "仓库中";
        }
        if(GoodsStatusEnum.AUDITTING.getGoodsStatus().equals(this.getGoodsSellStatus())){
            return "审核中";
        }
        if(GoodsStatusEnum.SELLING.getGoodsStatus().equals(this.getGoodsSellStatus())){
            return "销售中";
        }
        if(GoodsStatusEnum.SELLING_OFF_REQUEST.getGoodsStatus().equals(this.getGoodsSellStatus()) || GoodsStatusEnum.SELLING_OFF_FRONT.equals(this.getGoodsSellStatus())){
            return "下架中";
        }

        return "";
    }

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

    public String getGooodsAttribute() {
        return gooodsAttribute;
    }

    public void setGooodsAttribute(String gooodsAttribute) {
        this.gooodsAttribute = gooodsAttribute;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
}
