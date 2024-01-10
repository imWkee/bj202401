package com.github.admin.common.utils;


import java.util.regex.Pattern;


public class ShiroUtil {

    /**
     * 多个IP的分隔符
     */
    private static final char IP_SPLIT = ',';

    /**
     * IP验证正则对象
     */
    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    /**
     * 加密算法
     */
    public final static String HASH_ALGORITHM_NAME = EncryptUtil.HASH_ALGORITHM_NAME;

    /**
     * 循环次数
     */
    public final static int HASH_ITERATIONS = EncryptUtil.HASH_ITERATIONS;

    /**
     * 加密处理（64位字符）
     * 备注：采用自定义的密码加密方式，其原理与SimpleHash一致，
     * 为的是在多个模块间可以使用同一套加密方式，方便共用系统用户。
     *
     * @param password 密码
     * @param salt     密码盐
     */
    public static String encrypt(String password, String salt) {
        return EncryptUtil.encrypt(password, salt, HASH_ALGORITHM_NAME, HASH_ITERATIONS);
    }

    /**
     * 获取随机盐值
     */
    public static String getRandomSalt() {
        return EncryptUtil.getRandomSalt();
    }

    /**
     * 获取当前用户对象
     */
//    public static User getSubject() {
//        User user = (User) SecurityUtils.getSubject().getPrincipal();
//
//        // 初始化延迟加载的部门信息
//        if (user != null && !Hibernate.isInitialized(user.getDept())) {
//            try {
//                Hibernate.initialize(user.getDept());
//            } catch (LazyInitializationException e) {
//                // 部门数据延迟加载超时，重新查询用户数据（用于更新“记住我”状态登录的数据）
//                UserService userService = SpringContextUtil.getBean(UserService.class);
//                User reload = userService.getById(user.getId());
//                Hibernate.initialize(reload.getDept());
//                // 将重载用户数据拷贝到登录用户中
//                BeanUtils.copyProperties(reload, user, "roles");
//            }
//        }
//
//        return user;
//    }

    /**
     * 获取当前用户角色列表
     */
//    public static Set<Role> getSubjectRoles() {
//        User user = (User) SecurityUtils.getSubject().getPrincipal();
//
//        // 如果用户为空，则返回空列表
//        if (user == null) {
//            user = new User();
//        }
//
//        // 判断角色列表是否已缓存
//        if (!Hibernate.isInitialized(user.getRoles())) {
//            try {
//                Hibernate.initialize(user.getRoles());
//            } catch (LazyInitializationException e) {
//                // 延迟加载超时，重新查询角色列表数据
//                RoleService roleService = SpringContextUtil.getBean(RoleService.class);
//                user.setRoles(roleService.getUserOkRoleList(user.getId()));
//            }
//        }
//
//        return user.getRoles();
//    }

    /**
     * 获取用户IP地址
     */
//    public static String getIp() {
//        HttpServletRequest request = HttpServletUtil.getRequest();
//        // 反向代理时获取真实ip
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("X-Forwarded-For");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("X-Real-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//
//        // 校验IP地址，防止恶意伪造请求头信息，暂时只允许ipv4的地址
//        if (StringUtils.hasText(ip)) {
//            ip = ip.substring(ip.lastIndexOf(IP_SPLIT) + 1).trim();
//            if (!IP_PATTERN.matcher(ip).matches()) {
//                ip = "ipv4 unknown";
//            }
//        }
//
//        return ip;
//    }
}
