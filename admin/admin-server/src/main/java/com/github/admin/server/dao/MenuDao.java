package com.github.admin.server.dao;

import com.github.admin.common.domain.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao {

    List<Menu> findMenuByMenuIds(@Param("menuIdList") List<Long> menuIdList);
}
