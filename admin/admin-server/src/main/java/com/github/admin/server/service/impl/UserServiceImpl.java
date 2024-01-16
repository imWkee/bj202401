package com.github.admin.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.admin.common.domain.User;
import com.github.admin.common.enums.AdminErrorMsgEnum;
import com.github.admin.common.request.UserRequest;
import com.github.admin.common.utils.ShiroUtil;
import com.github.admin.server.dao.UserDao;
import com.github.admin.server.service.UserService;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
        if(StringUtils.isBlank(userRequest.getOrderByColumn()) || StringUtils.isBlank(userRequest.getAsc())) {
            userRequest.setOrderByColumn("create_date");
            userRequest.setAsc("desc");
        }
        // 通过java反射机制把对象转换map
        Map<String, Object> map = BeanUtil.beanToMap(userRequest);
        map.put("startIndex", dataPage.getStartIndex());
        map.put("endIndex", dataPage.getEndIndex());


        long totalCount = userDao.findUserByPageCount(map);
        List<User> dataList = userDao.findUserByPageList(map);
        dataPage.setTotalCount(totalCount);
        dataPage.setDataList(dataList);
        return Result.ok(dataPage);
    }

    @Override
    public Result saveUser(UserRequest request) {
        if (request == null) {
            log.error("添加用户请求参数对象为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAM_IS_EMPTY);
        }
        if (StringUtils.isBlank(request.getUserName())){
            log.error("添加用户账号为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAM_IS_EMPTY);
        }
        if (StringUtils.isBlank(request.getPassword())){
            log.error("添加用户密码为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAM_IS_EMPTY);
        }
        if (StringUtils.isBlank(request.getConfirm())){
            log.error("添加用户确认密码为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAM_IS_EMPTY);
        }

        String userName = request.getUserName();
        String password = request.getPassword();
        String confirmPwd = request.getConfirm();

        if(!StringUtils.equals(password,confirmPwd)){
            log.error("添加用户密码和确认密码不一致,password = {},confirmPwd = {}",password,confirmPwd);
            return Result.fail(AdminErrorMsgEnum.USER_PASSWORD_IS_NOT_SAME);
        }
        if (userName.length() != StringUtils.deleteWhitespace(userName).length()) {
            log.error("添加用户账号存在空格");
            return Result.fail(AdminErrorMsgEnum.USER_NAME_HAS_INCLUDE_BLANK);
        }
        userName = StringUtils.deleteWhitespace(userName);

        User existUser = userDao.findUserByUserName(userName);
        if(existUser != null) {
            log.error("添加用户当前账号已经存在,userName = {}",userName);
            return Result.fail(AdminErrorMsgEnum.USER_NAME_IS_EXIST);
        }

        User user = new User();
        // 把request对象数据拷贝到user对象
        BeanUtils.copyProperties(request, user);
        Date date = new Date();
        user.setCreateDate(date);
        user.setUpdateDate(date);
        user.setUserName(userName);
        // 获取加密盐
        String salt = ShiroUtil.getRandomSalt();
        // 加密
        String pwd = ShiroUtil.encrypt(password, salt);
        user.setSalt(salt);
        user.setPassword(pwd);
        int row = userDao.insert(user);
        // 添加成功返回row == 1,其它数据都是失败
        if(row != 1) {
            log.error("添加用户操作失败");
            return Result.fail(AdminErrorMsgEnum.OPERATION_FAIL);
        }
        return Result.ok();
    }

    @Override
    public Result<User> findUserById(Long id) {
        if (id == null) {
            log.error("查询用户id参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAM_IS_EMPTY);
        }
        User user = userDao.findUserByPrimaryKey(id);
        if(user == null){
            log.error("查询用户id = {}数据不存在", id);
            return Result.fail(AdminErrorMsgEnum.DATA_IS_NOT_EXIST);
        }
        return Result.ok(user);
    }



}
