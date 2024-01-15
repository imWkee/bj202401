package com.github.admin.server.service;

import com.github.admin.common.domain.User;
import com.github.admin.common.request.UserRequest;
import com.github.framework.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Time: 2024/1/10
 * @Author: Wangke
 * @Description: 测试UserService接口
 * @FileName: UserServiceTest.java
 * @Software: IntelliJ IDEA
 **/

@Slf4j
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userServiceImpl;

    @Test
    public void _根据用户账号查询用户() {

        Result<User> result = userServiceImpl.findUserByUserName("admin");

        log.info("查询用户数据返回code = {},message = {}",result.getCode(),result.getMessage());

    }

    @Test
    public void _添加用户() {

        UserRequest user = new UserRequest();
        user.setUserName("Test");
        user.setNickName("测试");
        user.setPassword("123456");
        user.setConfirm("123456");
        user.setPhone("09610086");
        user.setEmail("123@163.com");
        user.setStatus(1);
        user.setSex(1);

        Result result = userServiceImpl.saveUser(user);

        log.info("添加用户数据返回code = {},message = {}",result.getCode(),result.getMessage());

    }


}
