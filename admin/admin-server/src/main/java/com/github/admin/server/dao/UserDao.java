package com.github.admin.server.dao;

import com.github.admin.common.domain.User;

public interface UserDao {

    User findUserByUserName(String userName);

}
