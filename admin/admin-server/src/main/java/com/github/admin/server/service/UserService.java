package com.github.admin.server.service;

import com.github.admin.common.domain.User;
import com.github.framework.core.Result;

public interface UserService {

    /**
     * 根据账号查询用户
     * @param userName
     * @return
     */
    Result<User> findUserByUserName(String userName);
}
