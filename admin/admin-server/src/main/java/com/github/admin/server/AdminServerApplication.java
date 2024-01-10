package com.github.admin.server;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Time: 2024/1/9
 * @Author: Wangke
 * @Description:
 * @FileName: AdminServerApplication.java
 * @Software: IntelliJ IDEA
 **/

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.github.admin.server.dao")
public class AdminServerApplication {
    public static void main(String[] args) {

        SpringApplication.run(AdminServerApplication.class);
        log.error(">>>>>>>>>>>>>>>admin-server启动成功>>>>>>>>>>>>>>>");

    }
}
