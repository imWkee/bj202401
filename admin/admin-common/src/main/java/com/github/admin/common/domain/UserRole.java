package com.github.admin.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @Time: 2024/1/9
 * @Author: Wangke
 * @Description:
 * @FileName: UserRole.java
 * @Software: IntelliJ IDEA
 **/

@Data
public class UserRole implements Serializable {

    private Long userId;
    private Long roleId;
}
