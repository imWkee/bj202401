package com.github.admin.common.enums;

import com.github.framework.core.exception.IError;

public enum AdminErrorMsgEnum  implements IError {

    USER_LOGIN_CAPTCHA_IS_EMPTY("100001","请输入正确的验证码"),
    USER_ACCOUNT_HAS_BEEN_FROZEN("100002","账号已冻结"),

    USER_NAME_OR_PASSWORD_IS_INCORRECT("100003","账号没有授权"),

    USER_IS_NOT_ADMIN("100004","当前用户不是管理员"),

    SYSTEM_EXCEPTION("100099","系统异常")

    ;

    private String code;
    private String message;

    AdminErrorMsgEnum(String code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }


}
