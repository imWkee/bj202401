package com.github.admin.common.request;

import com.github.framework.core.common.base.BaseRequest;
import lombok.Data;

/**
 * @Time: 2024/1/15
 * @Author: Wangke
 * @Description:
 * @FileName: UserRequest.java
 * @Software: IntelliJ IDEA
 **/

@Data
public class UserRequest extends BaseRequest {

    private String userName;
    private String nickName;
    private String password;
    private String confirm;
    private String phone;
    private String email;
    private Integer status;
    private Integer sex;
    private String remark;

}
