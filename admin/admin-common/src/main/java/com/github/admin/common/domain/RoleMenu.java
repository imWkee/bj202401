package com.github.admin.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @Time: 2024/1/9
 * @Author: Wangke
 * @Description:
 * @FileName: RoleMenu.java
 * @Software: IntelliJ IDEA
 **/

@Data
public class RoleMenu implements Serializable {

    private Long roleId;
    private Long menuId;
}
