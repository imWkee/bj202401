package com.github.admin.server.service.impl;

import com.github.admin.common.domain.User;
import com.github.admin.common.enums.AdminErrorMsgEnum;
import com.github.admin.server.dao.UserDao;
import com.github.admin.server.service.UserService;
import com.github.framework.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @Time: 2024/1/10
 * @Author: Wangke
 * @Description:
 * @FileName: UserServiceImpl.java
 * @Software: IntelliJ IDEA
 **/

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public Result<User> findUserByUserName(String userName){
        if (StringUtils.isBlank(userName)){
            log.error("查询用户userName参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAM_IS_EMPTY);
        }

        User user = userDao.findUserByUserName(userName);
        if (user == null) {
            log.error("根据userName = {}查询用户数据为空",userName);
            return Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }
        return Result.ok(user);
    }
}
