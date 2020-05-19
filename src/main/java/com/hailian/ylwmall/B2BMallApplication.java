package com.hailian.ylwmall;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("com.hailian.ylwmall.dao")
@EnableDubboConfiguration
@SpringBootApplication
public class B2BMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(B2BMallApplication.class, args);
    }
}
