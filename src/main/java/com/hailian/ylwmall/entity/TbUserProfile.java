package com.hailian.ylwmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 19012964
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbUserProfile extends Model<TbUserProfile> {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "profile_id", type = IdType.AUTO)
    private Long profileId;

    /**
     * 用户主键id
     */
    private Long userId;

    /**
     * 公司名
     */
    private String companyName;

    /**
     * 纳税人识别号
     */
    private String taxNo;

    /**
     * 营业执照号
     */
    private String compLegalNo;

    /**
     * 营业执照附件
     */
    private String compLegalUrl;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 注册地址
     */
    private String compAddr;

    /**
     * 联系人
     */
    private String contactor;

    /**
     * 联系人电话
     */
    private String contactorPhone;

    /**
     * 联系人邮箱
     */
    private String contactorEmail;

    /**
     * 财务联系人
     */
    private String financer;

    /**
     * 财务联系人电话
     */
    private String financerPhone;

    /**
     * 开票类型01-增值；02-普通
     */
    private String taxType;

    /**
     * 银行开户全称
     */
    private String bankHolder;

    /**
     * 银行账户
     */
    private String bankAccNo;

    /**
     * 开户行名称
     */
    private String bankName;

    /**
     * 开户行代码
     */
    private String bankCode;

    /**
     * 供应商承诺书
     */
    private String promiseUri;

    /**
     * 创建时间
     */
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.profileId;
    }

}
