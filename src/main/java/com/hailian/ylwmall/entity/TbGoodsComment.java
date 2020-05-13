package com.hailian.ylwmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.xml.internal.ws.spi.db.DatabindingException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 19012964
 * @since 2020-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbGoodsComment extends Model<TbGoodsComment> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 用户id
     */
    private Long userId;

    private Long orderId;

    /**
     * 用户名
     */
    private String userName;

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

    /**
     * 是否自动评价：0是 1否
     */
    private Integer isAuto;

    /**
     * 是否匿名：0是 1否
     */
    private Integer isAnonymous;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
