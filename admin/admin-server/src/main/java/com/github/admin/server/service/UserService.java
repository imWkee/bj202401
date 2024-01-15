package com.github.admin.server.service;

import com.github.admin.common.domain.User;
import com.github.admin.common.request.UserRequest;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;

public interface UserService {

    /**
     * 根据账号查询用户
     * @param userName
     * @return
     */
    Result<User> findUserByUserName(String userName);

    Result<DataPage<User>> findUserByPage(UserRequest userRequest);
}
