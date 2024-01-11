package com.github.admin.server.dao;

import com.github.admin.common.domain.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuDao {

    List<RoleMenu> findRoleMenuByRoleIds(@Param("roleIds") List<Long> roleIds);
}
