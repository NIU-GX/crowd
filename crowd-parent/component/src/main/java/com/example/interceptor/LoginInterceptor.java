package com.example.interceptor;

import com.example.entity.Admin;
import com.example.util.ConstantUtil;
import com.example.util.exception.AccessForbidenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author NGX
 * @Date 2020/5/3 16:05
 *
 * 拦截器：
 *  实现方法：
 *      1. 实现HanderIntercoptor接口，但是要实现三个方法
 *      2. 继承HandlerInterceptorAdapter,然后重写方法
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 先获取session对象
        HttpSession session = request.getSession();

        // 2. 尝试获取Admin对象
        Admin admin = (Admin) session.getAttribute(ConstantUtil.ATTR_NAME_LOGIN_ADMIN);

        // 3. 判断admin是否为空
        if (admin == null) {
            // 4. 抛出异常，访问被拒绝异常
            throw new AccessForbidenException(ConstantUtil.MESSAGE_ACCESS_FORBIDEN);
        }
        // 5. 如果admin不为空，返回true放行
        return true;
    }
}
