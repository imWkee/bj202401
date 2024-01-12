package com.github.admin.server.controller;

import com.github.admin.common.domain.Role;
import com.github.admin.server.service.RoleService;
import com.github.framework.core.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Time: 2024/1/11
 * @Author: Wangke
 * @Description:
 * @FileName: RoleController.java
 * @Software: IntelliJ IDEA
 **/

@RestController
public class RoleController {

    @Resource
    private RoleService roleServiceImpl;

    @GetMapping("/findRoleByUserId/{userId}")
    Result<Boolean> findRoleByUserId(@PathVariable("userId") Long userId){
        return roleServiceImpl.findRoleByUserId(userId);
    }

    @GetMapping("/findRolePermissionsByUserId/{userId}")
    Result<Set<Role>> findRolePermissionsByUserId(@PathVariable("userId") Long userId){

        return null;
    };

}
