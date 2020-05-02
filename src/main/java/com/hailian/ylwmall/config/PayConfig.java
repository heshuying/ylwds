package com.hailian.ylwmall.config;

import com.kjtpay.gateway.common.util.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayConfig {

    @Autowired
    KjtConfig kjtConfig;

    @Bean("securityService")
    SecurityService getSecurityService(){
        return new SecurityService(kjtConfig.getPrivatekey(), kjtConfig.getKjtpublickey());
    }
}
