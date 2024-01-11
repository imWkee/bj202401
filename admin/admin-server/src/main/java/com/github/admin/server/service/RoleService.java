package com.github.admin.server.service;

import com.github.framework.core.Result;
import org.springframework.web.bind.annotation.RequestParam;

public interface RoleService {

    Result<Boolean> findUserRoleByUserId(Long userId);
}
