package com.github.admin.server.controller;

import com.github.admin.common.domain.User;
import com.github.admin.server.service.UserService;
import com.github.framework.core.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    // @Autowired
    private UserService userServiceImpl;

    @GetMapping("/findUserByUserName")
    Result<User> findUserByUserName(@RequestParam("userName") String userName){
        return userServiceImpl.findUserByUserName(userName);
    }

}
