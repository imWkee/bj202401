package com.github.admin.server.service.impl;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.github.admin.common.domain.*;
import com.github.admin.common.enums.AdminErrorMsgEnum;
import com.github.admin.server.dao.*;
import com.github.admin.server.service.RoleService;
import com.github.framework.core.Result;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
    public Result<Boolean> findRoleByUserId(Long userId) {
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

        // 从查询出来的用户角色集合中通过STREAM流对角色ID收集
        List<Long> roleIds = userRoleList.stream().
                map(userRole -> userRole.getRoleId()).
                collect(Collectors.toList());

        // 查询角色集合，然后过滤状态启用的角色
        List<Role> roleList = roleDao.findRoleByRoleIds(roleIds)
                .stream().filter(role -> role.getStatus() == 1)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(roleList)) {
            log.error("当前userId = {}没有对应角色", userId);
            return Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }

        List<RoleMenu> roleMenuList = roleMenuDao.findRoleMenuByRoleIds(roleIds);

        if(CollectionUtils.isEmpty(roleMenuList)) {
            log.error("当前userId = {}查询角色菜单数据不存在", userId);
            return  Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }

        List<Long> menuIdList = roleMenuList.stream()
                .map(roleMenu -> roleMenu.getMenuId())
                .collect(Collectors.toList());

        List<Menu> menuList = menuDao.findMenuByMenuIds(menuIdList)
                .stream().filter(menu -> menu.getStatus() == 1)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(menuList)) {
            log.error("当前userId = {}查询菜单数据不存在",userId);
            return  Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }

        log.info("当前userId = {}查询角色授权成功",userId);
        return Result.ok(Boolean.TRUE);
    }

    @Override
    public Result<Set<Role>> findRolePermissionsByUserId(Long userId) {
        if (userId == null) {
            log.error("查询角色授权userId参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAM_IS_EMPTY);
        }
        User user = userDao.findUserByPrimaryKey(userId);
        if (user == null) {
            log.error("查询角色授权对应用户数据为空,userId = {}",userId);
            return Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }
        if (user.getStatus() != 1){
            log.error("查询角色授权对应用户已冻结, userId = {}",userId);
            return Result.fail(AdminErrorMsgEnum.USER_ACCOUNT_HAS_BEEN_FROZEN);
        }

        List<UserRole> userRoleList = userRoleDao.findUserRoleByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleList)){
            log.error("查询角色授权用户角色数据为空,userId ={}",userId);
            return Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }
        List<Long> roleIds = userRoleList.stream()
                .map(userRole -> userRole.getRoleId())
                .collect(Collectors.toList());
        List<Role> roleList = roleDao.findRoleByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(roleList)){
            log.error("查询角色授权对应角色为空,userId = {}",userId);
            return Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }

        for(Role role:roleList){
            Long roleId = role.getId();

            List<RoleMenu> roleMenuList = roleMenuDao.findRoleMenuByRoleIds(Arrays.asList(roleId));
            if (CollectionUtils.isEmpty(roleMenuList)){
                log.error("查询角色授权没有对应角色菜单,roleId = {}", roleId);
                continue;
            }
            List<Long> menuIds = roleMenuList.stream()
                    .map(roleMenu -> roleMenu.getMenuId())
                    .collect(Collectors.toList());

            List<Menu> menuList = menuDao.findMenuByMenuIds(menuIds)
                    .stream().filter(menu -> menu.getStatus()  == 1)
                    .collect(Collectors.toList());

            Set<Menu> menuSet = menuList.stream().collect(Collectors.toSet());
            role.setMenus(menuSet);
        }

        Set<Role> roleSet = roleList.stream().collect(Collectors.toSet());

        log.error("查询角色授权成功,userId = {}",userId);
        return Result.ok(roleSet);
    }
}
