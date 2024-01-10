package com.github.admin.api.controller;

import cn.hutool.core.util.RandomUtil;
import com.github.admin.api.properties.ProjectProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Time: 2024/1/10
 * @Author: Wangke
 * @Description: 接受前端的数据到请求页面
 * @FileName: LoginController.java
 * @Software: IntelliJ IDEA
 **/

@Controller
@Slf4j
public class LoginController {

    @Resource
    private ProjectProperties properties;

    @GetMapping(value = {"/login", "/"})
    public String login(Model model, HttpServletResponse response) {
        boolean captchaOpen = properties.isCaptchaOpen();
        log.info("请求验证码开关,captchaOpen:{}", captchaOpen);
        model.addAttribute("isCaptcha", captchaOpen);
        response.setHeader("token", RandomUtil.randomString(20));
        response.setHeader("uid",RandomUtil.randomNumbers(10));
        return "login";
    }
}
