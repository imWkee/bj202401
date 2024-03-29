package com.github.admin.api.controller;

import com.github.admin.client.UserServiceClient;
import com.github.admin.common.domain.User;
import com.github.admin.common.group.AddUserGroup;
import com.github.admin.common.request.UserRequest;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Time: 2024/1/15
 * @Author: Wangke
 * @Description:
 * @FileName: UserController.java
 * @Software: IntelliJ IDEA
 **/

@Controller
public class UserController {

    @Resource
    private UserServiceClient userServiceClient;

    @GetMapping("/main/system/user/index")
    @RequiresPermissions("system:user:index")
    public String index(Model model, UserRequest userRequest) {
        Result<DataPage<User>> result = userServiceClient.findUserByPage(userRequest);
        List<User> list = new ArrayList<User>();
        DataPage<User> dataPage = new DataPage<>();
        if(result.isSuccess()) {
            list = result.getData().getDataList();
            dataPage = result.getData();
            model.addAttribute("list", list);
            model.addAttribute("page", dataPage);
        }
        return "manager/user/index";
    }

    @GetMapping("/system/user/add")
    @RequiresPermissions("system:user:add")
    public String add() {
        return "manager/user/add";
    }

    @PostMapping("/system/user/save")
    @RequiresPermissions("system:user:add")
    @ResponseBody
    public Result add(@Validated(value = AddUserGroup.class) UserRequest request) {
        return userServiceClient.saveUser(request);
    }

    @GetMapping("/system/user/edit/{id}")
    @RequiresPermissions("system:user:edit")
    public String edit(@PathVariable("id")Long id, Model model) {
        User user = new User();
        Result<User> result = userServiceClient.findUserById(id);
        if(result.isSuccess()){
            user = result.getData();
            model.addAttribute("user",user);
        }
        return "manager/user/edit";
    }


}
