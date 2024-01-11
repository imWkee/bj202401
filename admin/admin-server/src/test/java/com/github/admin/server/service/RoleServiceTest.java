package com.github.admin.server.service;

import com.github.framework.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Time: 2024/1/11
 * @Author: Wangke
 * @Description: 测试RoleService接口
 * @FileName: RoleServiceTest.java
 * @Software: IntelliJ IDEA
 **/

@Slf4j
@SpringBootTest
public class RoleServiceTest {

    @Resource
    private RoleService roleServiceImpl;

    @Test
    public void _根据用户ID查询用户() {

        Result<Boolean> result = roleServiceImpl.findUserRoleByUserId(1L);

        log.info("查询用户数据返回code = {},message = {}",result.getCode(),result.getMessage());

    }
}
