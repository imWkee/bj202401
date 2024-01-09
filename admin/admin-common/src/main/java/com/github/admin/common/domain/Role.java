package com.github.admin.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @Time: 2024/1/9
 * @Author: Wangke
 * @Description:
 * @FileName: Role.java
 * @Software: IntelliJ IDEA
 **/

@Data
public class Role implements Serializable {

    private Long id;
    private String title;
    private String name;
    private String remark;
    private Date createData;
    private Date updateData;
    private Long createBy;
    private Long updateBy;
    private Integer status;

}
