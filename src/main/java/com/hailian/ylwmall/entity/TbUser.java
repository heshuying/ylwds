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
public class TbUser extends Model<TbUser> {

    private static final long serialVersionUID=1L;

    /**
     * 用户主键id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 登陆名称(默认为手机号)
     */
    private String loginName;

    /**
     * MD5加密后的密码
     */
    private String passwordMd5;

    /**
     * 个性签名
     */
    private String introduceSign;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 手机号
     */
    private String cellphone;

    /**
     * 01:个人客户;02:商户；03平台；04：供应商
     */
    private String userType;

    /**
     * 0-待审核 1-正常 2-锁定；3-审核不通过
     */
    private Integer userStatus;

    /**
     * 审核备注
     */
    private String checkMsg;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * mdm编码
     */
    private String mdmCode;

    /**
     * mdm返回的税号
     */
    private String outTaxCode;

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
