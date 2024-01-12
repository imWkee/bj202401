package com.github.admin.server.service.impl;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.github.admin.common.domain.*;
import com.github.admin.common.enums.AdminErrorMsgEnum;
import com.github.admin.server.dao.*;
import com.github.admin.server.service.MenuService;
import com.github.framework.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import javax.annotation.Resource;
import java.util.stream.Collectors;

import static org.reflections.Reflections.log;

/**
 * @Time: 2024/1/12
 * @Author: Wangke
 * @Description:
 * @FileName: MenuServiceImpl.java
 * @Software: IntelliJ IDEA
 **/

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Resource
    private UserDao userDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private RoleMenuDao roleMenuDao;

    @Resource
    private MenuDao menuDao;

    @Override
    public Result<TreeMap<Long, Menu>> findMenuByUserId(Long userId){

        if (userId == null) {
            log.error("查询用户菜单权限userId为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAM_IS_EMPTY);
        }
        User user = userDao.findUserByPrimaryKey(userId);
        if (userId == null) {
            log.error("查询用户菜单权限用户为空, userId = {}",userId);
            return Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }
        if (user.getStatus() != 1) {
            log.error("查询用户菜单权限用户已禁用, userId = {}",userId);
            return  Result.fail(AdminErrorMsgEnum.USER_ACCOUNT_HAS_BEEN_FROZEN);
        }
        List<UserRole> userRoleList = userRoleDao.findUserRoleByUserId(userId);
        if(CollectionUtils.isEmpty(userRoleList)){
            log.error("查询用户菜单权限对应用户角色数据为空,userId= {}",userId);
            return Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }

        List<Long> roleIds = userRoleList.stream().
                map(userRole -> userRole.getRoleId()).
                collect(Collectors.toList());

        List<Role> roleList = roleDao.findRoleByRoleIds(roleIds).stream()
                .filter(role -> role.getStatus() == 1)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(roleList)) {
            log.error("查询用户菜单权限对应角色数据为空,userId = {}", userId);
            return Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }

        List<RoleMenu> roleMenuList = roleMenuDao.findRoleMenuByRoleIds(roleIds);

        if(CollectionUtils.isEmpty(roleMenuList)) {
            log.error("查询角色菜单权限对应角色菜单数据为空,userId = {}", userId);
            return  Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }

        List<Long> menuIds = roleMenuList.stream()
                .map(roleMenu -> roleMenu.getMenuId())
                .collect(Collectors.toList());

        List<Menu> menuList = menuDao.findMenuByMenuIds(menuIds).stream()
                .filter(menu -> menu.getStatus() == 1)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(menuList)) {
            log.error("查询用户菜单权限对应菜单权限为空,userId = {}",userId);
            return  Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }
        TreeMap<Long, Menu> treeMap = new TreeMap<Long, Menu>();
        Map<Long, Menu> keyMenu = menuList.stream()
                .collect(Collectors.toMap(menu -> menu.getId(), menu -> menu));

        keyMenu.forEach((key, menu) -> {
            // 1. 目录 2. 菜单 3. 按钮
            if (menu.getType() != 3) {
                if (keyMenu.get(menu.getPid()) != null) {
                    keyMenu.get(menu.getPid()).getChildMap()
                            .put(Long.valueOf(menu.getSort()),menu);
                } else {
                    if (menu.getType() == 1) {
                        treeMap.put(Long.valueOf(menu.getSort()),menu);
                    }
                }
            }
        });
        if(MapUtils.isEmpty(treeMap)) {
            log.error("查询用户菜单权限对应菜单map集合数据为空, userId = {}", userId);
            return Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }

        return Result.ok(treeMap);
    };
}
