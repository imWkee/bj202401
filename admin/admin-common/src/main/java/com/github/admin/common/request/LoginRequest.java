package com.github.admin.common.request;

import com.github.framework.core.common.base.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest extends BaseRequest {


    @NotBlank(message = "账户不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String captcha;

    private String rememberMe;

}
