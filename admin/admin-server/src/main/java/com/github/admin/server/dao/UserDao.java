package com.github.admin.server.dao;

import com.github.admin.common.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    User findUserByUserName(String userName);

    User findUserByPrimaryKey(Long id);

    long findUserByPageCount(Map<String, Object> map);

    List<User> findUserByPageList(Map<String, Object> map);

    int insert(User user);



}
