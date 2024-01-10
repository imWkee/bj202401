package com.github.admin.common.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRole implements Serializable {

    private Long userId;
    private Long roleId;


}
