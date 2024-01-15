package com.github.admin.common.enums;

import com.github.framework.core.exception.IError;

public enum AdminErrorMsgEnum  implements IError {

    USER_LOGIN_CAPTCHA_IS_EMPTY("10001","请输入正确的验证码"),
    USER_ACCOUNT_HAS_BEEN_FROZEN("10002","账号已冻结"),

    USER_NAME_OR_PASSWORD_IS_INCORRECT("10003","账号没有授权"),

    USER_IS_NOT_ADMIN("10004","当前用户不是管理员"),

    REQUEST_PARAM_IS_EMPTY("10005","请求参数为空"),

    DATA_IS_NOT_EXIST("10006","数据不存在"),

    USER_PASSWORD_IS_NOT_SAME("10007","用户输入密码不一致"),

    OPERATION_FAIL("10098","操作失败"),
    SYSTEM_EXCEPTION("10099","系统异常")

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
