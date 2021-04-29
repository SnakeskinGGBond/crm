package com.jbp.crm.handler;

import com.jbp.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author SQueen
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        if ("/login.jsp".equals(path) || "/user/login".equals(path)) {
            return true;
        } else {
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                //请求放行
                return true;
            } else {
            /*
                不使用请求转发:转发之后,路径会停留在老路径上,而不是跳转之后的路径
                我们应该在为用户跳转到登录页的同事,将浏览器的地址栏自动设置为当前的登录页地址

                重定向使用的事绝对路径,前面必须 以 /项目名 开头
             */
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return false;
            }
        }

    }
}
