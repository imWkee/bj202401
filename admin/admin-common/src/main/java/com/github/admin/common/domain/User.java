package com.github.admin.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @Time: 2024/1/9
 * @Author: Wangke
 * @Description:
 * @FileName: User.java
 * @Software: IntelliJ IDEA
 **/

@Data
public class User implements Serializable {

    private Long id;
    private String userName;
    private String nickName; // 昵称
    private String password;
    private String salt;
    private String picture;
    private Integer sex;
    private String email;
    private String phone;
    private String remark;
    private Date createData;
    private Date updateData;
    private Integer status;

    // 用户已授权的角色
    private Set<Role> authSet = new HashSet<Role>();
    // 全部角色列表（包含已授权/未授权）
    private List<Role> list = new ArrayList<Role>();

}
