package com.github.admin.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class User implements Serializable {

    private Long id;
    private String userName;
    private String nickName;
    private String password;
    private String salt;
    private String picture;
    private Integer sex;
    private String email;
    private String phone;
    private String remark;
    private Date createDate;
    private Date updateDate;
    private Integer status;

    //用户已经授权的角色
    private Set<Role> authSet = new HashSet<Role>();
    //全部角色列表（包含授权/未授权）
    private List<Role> list = new ArrayList<Role>();

}
