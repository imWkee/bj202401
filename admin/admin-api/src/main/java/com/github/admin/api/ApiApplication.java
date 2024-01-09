package com.github.admin.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Time: 2024/1/9
 * @Author: Wangke
 * @Description:
 * @FileName: ApiApplication.java
 * @Software: IntelliJ IDEA
 **/

@Slf4j
@SpringBootApplication
@EnableFeignClients
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        log.error(">>>>>系统启动成功>>>>>");
    }
}
