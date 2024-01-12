package com.github.admin.server.service;

import com.github.admin.common.domain.Role;
import com.github.framework.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Set;

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
    public void _根据用户ID查询用户对应菜单() {

        Result<Boolean> result = roleServiceImpl.findRoleByUserId(1L);

        log.info("查询用户数据返回code = {},message = {}", result.getCode(), result.getMessage());

    }

    @Test
    public void _根据用户ID查询角色权限() {

        Result<Set<Role>> result = roleServiceImpl.findRolePermissionsByUserId(1L);

        log.info("查询用户数据返回code = {},message = {}", result.getCode(), result.getMessage());

    }


}
