package com.hailian.ylwmall.dto;

import lombok.Data;

/**
 * Created by 19012964 on 2020/4/15.
 */
@Data
public class RegisterFirstDto {
    private String loginName;
    private String password;
    private String confirmPwd;
    private String validCode;
    private String userType;
}
