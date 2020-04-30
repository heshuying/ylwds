package com.hailian.ylwmall.dto;

import lombok.Data;

/**
 * Created by 19012964 on 2020/4/15.
 */
@Data
public class LoginDto {
    private String loginName;
    private String password;
    private String verifyCode;
}
