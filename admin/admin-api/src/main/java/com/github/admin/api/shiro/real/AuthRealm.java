package com.github.admin.api.shiro.real;


import com.github.admin.common.constants.AdminConst;
import com.github.admin.common.domain.User;
import com.github.admin.common.utils.ShiroUtil;
import com.github.framework.core.exception.Ex;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;


@Slf4j
public class AuthRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRealm.class);

//    @Resource
//    private UserServiceClient userServiceClient;
//    @Resource
//    private RoleServiceClient roleServiceClient;

    /**
     * 授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取用户Principal对象
        User user = (User) principal.getPrimaryPrincipal();

        if (user == null) {
            throw new UnknownAccountException();
        }else if(!user.getStatus().equals(AdminConst.START_STATUS)) {
            throw new LockedAccountException();
        }
        Long userId = user.getId();
        // 管理员拥有所有权限
        if (userId.equals(AdminConst.ADMIN_ID)) {
            info.addRole(AdminConst.ADMIN_ROLE_NAME);
            info.addStringPermission("*:*:*");
            return info;
        }
//
        // 赋予角色和资源授权
//        Result<Set<Role>> result = roleServiceClient.findRolePermissionsByUserId(userId);
//        if(result.isSuccess()){
//            Set<Role> roles = result.getData();
//                    roles.forEach(role -> {
//                        info.addRole(role.getName());
//                        role.getMenus().forEach(menu -> {
//                            String perms = menu.getPerms();
//                            if (StringUtils.isNotBlank(perms) && !perms.contains("*")) {
//                                info.addStringPermission(perms);
//                            }
//                        });
//                    });
//        } else {
//            throw new UnknownAccountException();
//        }
        return info;

    }

    /**
     * 认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 获取数据库中的用户名密码
//        Result<User> result = userServiceClient.findUserByUserName(token.getUsername());
//        if (result.isSuccess()) {
//            User user = result.getData();
//            // 判断用户名是否存在
//            if (user == null) {
//                throw new UnknownAccountException();
//            }else if(!user.getStatus().equals(AdminConst.START_STATUS)) {
//                throw new LockedAccountException();
//            }
//            // 对盐进行加密处理
//            ByteSource salt = ByteSource.Util.bytes(user.getSalt());
//            /* 传入密码自动判断是否正确
//             * 参数1：传入对象给Principal
//             * 参数2：正确的用户密码
//             * 参数3：加盐处理
//             * 参数4：固定写法
//             */
//            return new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
//         }

         throw Ex.business("000000","系统异常");
    }

    /**
     * 自定义密码验证匹配器
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        setCredentialsMatcher(new SimpleCredentialsMatcher() {
            @Override
            public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
                UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
                SimpleAuthenticationInfo info = (SimpleAuthenticationInfo) authenticationInfo;
                // 获取明文密码及密码盐
                String password = String.valueOf(token.getPassword());
                String salt = CodecSupport.toString(info.getCredentialsSalt().getBytes());

                return equals(ShiroUtil.encrypt(password, salt), info.getCredentials());
            }
        });
    }
}
