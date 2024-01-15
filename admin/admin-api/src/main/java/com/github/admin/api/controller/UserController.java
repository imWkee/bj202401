package com.github.admin.api.controller;

import com.github.admin.client.UserServiceClient;
import com.github.admin.common.domain.User;
import com.github.admin.common.request.UserRequest;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}
