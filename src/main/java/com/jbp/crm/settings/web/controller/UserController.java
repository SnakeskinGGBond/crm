package com.jbp.crm.settings.web.controller;

import com.jbp.crm.exception.UserException;
import com.jbp.crm.settings.domain.User;
import com.jbp.crm.settings.service.UserService;
import com.jbp.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SQueen
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> userLogin(String loginAct, String loginPwd,
                                         HttpServletResponse response, HttpServletRequest request) throws UserException {
        //密码转换为MD5
        loginPwd = MD5Util.getMD5(loginPwd);
        //浏览器端ip
        String ip = request.getRemoteAddr();
        User user = userService.login(loginAct, loginPwd, ip);
        request.getSession().setAttribute("user", user);
        //如果程序执行到此,说明业务层没有抛出任何异常
        //表示登录成功
        /*
            {"success":true}
         */
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }
}
