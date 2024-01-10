package com.github.admin.api.utils;

import com.github.framework.core.page.DataPage;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class PageUtil {

    private String paramHref;

    public boolean pageInit(DataPage page, HttpServletRequest request){
        if (page.getTotalCount() > page.getPageSize() && page.getPageNo() != 0){

            // 获取分页参数地址
            String servletPath = request.getServletPath();
            StringBuffer param = new StringBuffer(servletPath + "?");
            Enumeration em = request.getParameterNames();
            while (em.hasMoreElements()) {
                String name = (String) em.nextElement();
                if(!"pageNo".equals(name)){
                    String value = request.getParameter(name);
                    param.append(name + "=" + value + "&");
                }
            }
            this.paramHref = param.toString();
            return true;
        }
        return false;
    }

    public List<String> pageCode(DataPage page){
        int number = page.getPageNo();
        long totalPages = page.getTotalPages();
        long start = 0;
        int length = 7;
        int half = length % 2 == 0 ? length / 2 : length / 2 + 1;
        List<String> codeList = new ArrayList<>();

        if(totalPages > length && number > half){
            start = number - half;
        }
        if(totalPages > length && number > totalPages - half){
            start = totalPages - length;
        }
        for (int i=1; i <= (totalPages > length ? length : totalPages); i++){
            codeList.add(String.valueOf( i + start));
        }
        if(totalPages > length && number > half){
            codeList.set(0, "1");
            codeList.set(1, "…");
        }
        if(totalPages > length && number < totalPages - (half-1)){
            codeList.set(length - 2, "…");
            codeList.set(length - 1, String.valueOf(totalPages));
        }
        return codeList;
    }

    public String pageActive(DataPage page, String pageCode, String className){
        int number = page.getPageNo();
        if(!"…".equals(pageCode)){
            if(number == Integer.valueOf(pageCode)){
                return " " + className;
            }
        }
        return "";
    }

    public boolean isPrevious(DataPage page){
        return page.isHasPrev();
    }

    public boolean isNext(DataPage page){
        return page.isHasNext();
    }

    public boolean isCode(String pageCode){
        return "…".equals(pageCode);
    }

    public String pageHref(String pageCode){
        return paramHref + "pageNo=" + pageCode;
    }
}
