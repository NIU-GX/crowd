package com.example.util;


import javax.servlet.http.HttpServletRequest;

/**
 * @author NGX
 * @Date 2020/5/1 10:20
 * @Description 判断请求是ajax还是普通请求
 */
public class CrowdUtil {
    /**
     * 判断请求是否是ajax请求
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Requested-With");
        return ((accept!=null && accept.contains("application/json")) || (xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest")));
    }
}
