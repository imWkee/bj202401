package com.github.admin.server.dao;

import com.github.admin.common.domain.UserRole;

import java.util.List;

public interface UserRoleDao {

    List<UserRole> findRoleByUserId(Long userId);
}
