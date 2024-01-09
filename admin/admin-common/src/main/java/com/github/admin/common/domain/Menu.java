package com.github.admin.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @Time: 2024/1/9
 * @Author: Wangke
 * @Description:
 * @FileName: Menu.java
 * @Software: IntelliJ IDEA
 **/

@Data
public class Menu implements Serializable {
    private Long id;
    private String title;
    private Long pid;
    private String pids;
    private String url;
    private String perms; // 权限标识
    private String icon;
    private Integer type;
    private int sort;
    private String remark;
    private Date createData;
    private Date updateData;
    private Long createBy;
    private Long updateBy;
    private Integer status;
}
