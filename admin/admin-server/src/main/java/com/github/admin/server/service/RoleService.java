package com.github.admin.server.service;

import com.github.admin.common.domain.Role;
import com.github.framework.core.Result;

import java.util.Set;

public interface RoleService {

    Result<Boolean> findRoleByUserId(Long userId);

    Result<Set<Role>> findRolePermissionsByUserId(Long userId);
}
