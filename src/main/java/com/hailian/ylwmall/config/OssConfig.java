package com.hailian.ylwmall.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by 01440590 on 2019/1/27.
 */
@Component
@ConfigurationProperties(prefix = "oss")
@Data
public class OssConfig {
    private String baseUrl;
    private String appName;
    private String appKey;
    private String appSecret;
}
