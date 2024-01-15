package com.github.admin.server.service.impl;

import com.github.admin.common.domain.User;
import com.github.admin.common.enums.AdminErrorMsgEnum;
import com.github.admin.common.request.UserRequest;
import com.github.admin.server.dao.UserDao;
import com.github.admin.server.service.UserService;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Time: 2024/1/10
 * @Author: Wangke
 * @Description: 实现类
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

    @Override
    public Result<DataPage<User>> findUserByPage(UserRequest userRequest) {
        int pageNo = userRequest.getPageNo();
        int pageSize = userRequest.getPageSize();
        DataPage<User> dataPage = new DataPage<>(pageNo, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("startIndex", dataPage.getStartIndex());
        map.put("endIndex", dataPage.getEndIndex());

        long totalCount = userDao.findUserByPageCount(map);
        List<User> dataList = userDao.findUserByPageList(map);
        dataPage.setTotalCount(totalCount);
        dataPage.setDataList(dataList);
        return Result.ok(dataPage);
    }
}
