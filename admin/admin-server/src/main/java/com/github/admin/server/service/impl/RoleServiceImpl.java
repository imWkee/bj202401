package com.github.admin.server.service.impl;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.github.admin.common.domain.User;
import com.github.admin.common.domain.UserRole;
import com.github.admin.common.enums.AdminErrorMsgEnum;
import com.github.admin.server.dao.*;
import com.github.admin.server.service.RoleService;
import com.github.framework.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Time: 2024/1/11
 * @Author: Wangke
 * @Description:
 * @FileName: RoleServiceImpl.java
 * @Software: IntelliJ IDEA
 **/

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Resource
    private UserDao userDao;
    @Resource
    private UserRoleDao userRoleDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private RoleMenuDao roleMenuDao;
    @Resource
    private MenuDao menuDao;

    @Override
    public Result<Boolean> findUserRoleByUserId(Long userId) {
        if (userId == null) {
            log.error("查询用户对应角色是否授权userId为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAM_IS_EMPTY);
        }
        User user = userDao.findUserByPrimaryKey(userId);
        if (user == null){
            log.error("查询userId = {}对应用户不存在",userId);
            return Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }
        if (user.getStatus() != 1) {
            log.error("当前userId = {}对应用户已冻结",userId);
            return  Result.fail(AdminErrorMsgEnum.USER_ACCOUNT_HAS_BEEN_FROZEN);
        }

        List<UserRole> userRoleList = userRoleDao.findUserRoleByUserId(userId);
        if(CollectionUtils.isEmpty(userRoleList)){
            log.error("当前userId = {}没有对应用户角色", userId);
            return Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }

        List<Long> roleId = userRoleList.stream().
                map(userRole -> userRole.getRoleId()).
                collect(Collectors.toList());

        return Result.ok();
    }
}
