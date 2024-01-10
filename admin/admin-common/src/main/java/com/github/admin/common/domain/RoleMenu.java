package com.github.admin.common.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleMenu implements Serializable {

    private Long roleId;
    private Long menuId;
}
