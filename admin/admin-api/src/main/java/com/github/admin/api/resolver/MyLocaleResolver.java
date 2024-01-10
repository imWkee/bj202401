package com.github.admin.api.resolver;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

//@Service
@Slf4j
public class MyLocaleResolver implements LocaleResolver {

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String lan = request.getParameter("lan");
		log.info("获取请求lan:{}",lan);
        if (StringUtils.isEmpty(lan)) {
        	lan = "zh_CN";
        }
        String[] ii = lan.split("_");
        return new Locale(ii[0], ii[1]);
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

	}

}
