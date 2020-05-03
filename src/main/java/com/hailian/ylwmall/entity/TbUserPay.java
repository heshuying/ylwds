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
 * @since 2020-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbUserPay extends Model<TbUserPay> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 协议号
     */
    private String tokenId;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 银行卡账户名
     */
    private String bankAccountName;

    /**
     * 安全码，信用卡必传
     */
    private String cvv2;

    /**
     * 信用卡有效期 YYYY/MM
     */
    private String validDate;

    /**
     * 手机号码，数字
     */
    private String phoneNum;

    /**
     * 证件类型参考
     */
    private String certificatesType;

    /**
     * 证件号码
     */
    private String certificatesNumber;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * token是否已验证：0未验证 1已验证
     */
    private int tokenIsvalid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
