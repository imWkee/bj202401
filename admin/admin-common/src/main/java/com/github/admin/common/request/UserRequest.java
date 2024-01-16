package com.github.admin.common.request;

import com.github.admin.common.group.AddUserGroup;
import com.github.admin.common.group.UpdateUserGroup;
import com.github.framework.core.common.base.BaseRequest;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @Time: 2024/1/15
 * @Author: Wangke
 * @Description:
 * @FileName: UserRequest.java
 * @Software: IntelliJ IDEA
 **/

@Data
public class UserRequest extends BaseRequest {

    @NotBlank(message = "用户名不能为空", groups = {AddUserGroup.class, UpdateUserGroup.class})
    private String userName;
    @NotBlank(message = "用户昵称不能为空", groups = {AddUserGroup.class, UpdateUserGroup.class})
    private String nickName;
    @NotBlank(message = "密码不能为空", groups = {AddUserGroup.class})
    private String password;
    @NotBlank(message = "确认密码不能为空", groups = {AddUserGroup.class})
    private String confirm;
    private String phone;
//    @Email(message = "邮箱格式错误", groups = {AddUserGroup.class, UpdateUserGroup.class})
    private String email;
    private Integer status;
    private Integer sex;
    private String remark;

}
