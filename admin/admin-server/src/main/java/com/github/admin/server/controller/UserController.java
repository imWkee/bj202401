package com.github.admin.server.controller;

import com.github.admin.common.domain.User;
import com.github.admin.common.request.UserRequest;
import com.github.admin.server.service.UserService;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Time: 2024/1/10
 * @Author: Wangke
 * @Description: 实现类
 * @FileName: UserController.java
 * @Software: IntelliJ IDEA
 **/

@RestController
public class UserController {

    @Resource
    private UserService userServiceImpl;

    @GetMapping("/findUserByUserName")
    Result<User> findUserByUserName(@RequestParam("userName") String userName){
        return userServiceImpl.findUserByUserName(userName);
    }

    @PostMapping("/findUserByPage")
    Result<DataPage<User>> findUserByPage(@RequestBody UserRequest userRequest) {
        return userServiceImpl.findUserByPage(userRequest);
    }

}
