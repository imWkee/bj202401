package com.github.admin.server.controller;

import com.github.framework.core.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Time: 2024/1/11
 * @Author: Wangke
 * @Description:
 * @FileName: RoleController.java
 * @Software: IntelliJ IDEA
 **/

public class RoleController {

    @GetMapping("/findRoleByUserId/{userId}")
    Result<Boolean> findRoleByUserId(@RequestParam("userId") String userId){
        return null;
    }
}
