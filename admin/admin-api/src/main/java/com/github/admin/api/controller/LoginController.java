package com.github.admin.api.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.github.admin.api.properties.ProjectProperties;
import com.github.admin.api.utils.URL;
import com.github.admin.common.domain.User;
import com.github.admin.common.enums.AdminErrorMsgEnum;
import com.github.admin.common.request.LoginRequest;
import com.github.admin.common.utils.CaptchaUtil;
import com.github.framework.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

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

    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }

    @GetMapping("/noAuth")
    public String noAuth() {
        return "/403";
    }

    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置响应头文件，通知浏览器不要缓存
        response.setHeader("Expires","-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma","-1");
        response.setContentType("image/jpeg");
        // 获取验证码
        String code = CaptchaUtil.getRandomCode();
        log.info("当前用户登录随机验证码code:{}",code);
        // 将验证码输入到session中，用来验证
        request.getSession().setAttribute("captcha", code);
        // 输出到web页面
        ImageIO.write(CaptchaUtil.genCaptcha(code), "jpg", response.getOutputStream());
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(@Validated LoginRequest loginRequest) {
        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();
        String captcha = loginRequest.getCaptcha();
        String rememberMe = loginRequest.getRememberMe();
        boolean isCaptcha = properties.isCaptchaOpen();
        log.info("当前系统配置是否需要验证码登录，isCaptcha:{}，用户输入验证码为，captcha:{}",isCaptcha,captcha);
        if(isCaptcha) {
            Session session = SecurityUtils.getSubject().getSession();
            String sessionCaptcha = (String) session.getAttribute("captcha");
            if (StringUtils.isBlank(captcha) || StringUtils.isBlank(sessionCaptcha)
            || !captcha.toUpperCase().equals(sessionCaptcha.toUpperCase())){
                return Result.fail(AdminErrorMsgEnum.USER_LOGIN_CAPTCHA_IS_EMPTY);
            }
            session.removeAttribute("captcha");
        }
        // 1. 获取Subject主体对象
        Subject subject = SecurityUtils.getSubject();
        // 2. 封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        // 3. 执行登录，进入自定义Realm类中
        try {
            // 判断是否自动登录
            if (rememberMe != null) {
                token.setRememberMe(true);
            } else {
                token.setRememberMe(false);
            }
            subject.login(token);
            // 判断是否拥有后台对象
            User user = (User) SecurityUtils.getSubject().getPrincipal();

            Result<Boolean> result = null;
            if (result.isSuccess()) {
                return Result.ok(new URL("/main"));
            } else {
                SecurityUtils.getSubject().logout();
                return Result.fail(AdminErrorMsgEnum.USER_IS_NOT_ADMIN);
            }
        } catch (LockedAccountException e) {
            log.error("用户冻结异常:",e);
            return Result.fail(AdminErrorMsgEnum.USER_ACCOUNT_HAS_BEEN_FROZEN);
        } catch (AuthenticationException e) {
            log.error("用户授权异常:",e);
            return  Result.fail(AdminErrorMsgEnum.USER_NAME_OR_PASSWORD_IS_INCORRECT);
        } catch (Exception e) {
            log.error("系统异常:",e);
            return Result.fail(AdminErrorMsgEnum.SYSTEM_EXCEPTION);
        }
    }


}



