package com.hailian.ylwmall.dto;

import lombok.Data;

/**
 * Created by 19012964 on 2020/4/16.
 */
@Data
public class UserListDto {
    private Long userId;
    /**
     * 登陆名称(默认为手机号)
     */
    private String loginName;

    /**
     * mdm编码
     */
    private String  mdmCode;

    /**
     * 01:个人客户;02:商户；03平台；04：供应商
     */
    private String userType;

    /**
     * 0-待审核 1-正常 2-锁定；3-审核不通过
     */
    private Integer userStatus;

    /**
     * 公司名
     */
    private String companyName;

    /**
     * 联系人
     */
    private String contactor;

    /**
     * 联系人电话
     */
    private String contactorPhone;
}
