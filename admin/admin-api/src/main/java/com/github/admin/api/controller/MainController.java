package com.github.admin.api.controller;

import com.github.admin.client.MenuServiceClient;
import com.github.admin.common.domain.Menu;
import com.github.admin.common.domain.User;
import com.github.framework.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.TreeMap;

/**
 * @Time: 2024/1/12
 * @Author: Wangke
 * @Description:
 * @FileName: MainController.java
 * @Software: IntelliJ IDEA
 **/

@Controller
@Slf4j
public class MainController {

    @Resource
    private MenuServiceClient menuServiceClient;

    @GetMapping("/main")
    public String main(Model model) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        TreeMap<Long, Menu> treeMenu = new TreeMap<>();
        Long userId = user.getId();
        Result<TreeMap<Long, Menu>> result = menuServiceClient.findMenuByUserId(userId);
        if(result.isSuccess()) {
            treeMenu = result.getData();
        }
        model.addAttribute("treeMenu",treeMenu);
        model.addAttribute("user",user);
        return "main";
    }
}
