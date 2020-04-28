package com.hailian.ylwmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 19012964
 * @since 2020-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbModuleDetail extends Model<TbModuleDetail> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    /**
     * 删除标识字段(0-未删除 1-已删除)
     */
    private Integer isDeleted;

    /**
     * 推荐理由1
     */
    private String desc1;

    /**
     * 推荐理由2
     */
    private String desc2;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建者id
     */
    private Integer createUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改者id
     */
    private Integer updateUser;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
