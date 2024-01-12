package com.github.admin.server.service;

import com.github.admin.common.domain.Menu;
import com.github.framework.core.Result;
import java.util.TreeMap;

public interface MenuService {

    /**
     * 根据用户ID查询菜单权限
     * @param userId
     * @return
     */
    Result<TreeMap<Long, Menu>> findMenuByUserId(Long userId);

}
