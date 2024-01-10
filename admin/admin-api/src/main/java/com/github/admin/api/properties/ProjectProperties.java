package com.github.admin.api.properties;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "project")
public class ProjectProperties {

    /** 是否开启验证码 */
    private boolean captchaOpen = false;

    /** 是否开启Swagger数据接口文档 */
    private boolean swaggerEnabled = true;

    /** xss防护设置 */
    private Xxs xxs = new Xxs();

    /**
     * xss防护设置
     */
    @Data
    public static class Xxs {
        /** xss防护开关 */
        private boolean enabled = true;

        /** 拦截规则，可通过“,”隔开多个 */
        private String urlPatterns = "/*";

        /** 默认忽略规则（无需修改） */
        private String defaultExcludes = "/favicon.ico,/img/*,/js/*,/css/*,/lib/*";

        /** 忽略规则，可通过“,”隔开多个 */
        private String excludes = "";

        /**
         * 拼接忽略规则
         */
        public String getExcludes() {
            if (StringUtils.isNotBlank(excludes.trim())) {
                return defaultExcludes + "," + excludes;
            }
            return defaultExcludes;
        }
    }
}
