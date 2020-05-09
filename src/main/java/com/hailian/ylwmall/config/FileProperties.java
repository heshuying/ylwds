package com.hailian.ylwmall.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "file")
public class FileProperties {
    private String uploadDir;
    private String staticAccessPath;
    private String uriPath;
}
