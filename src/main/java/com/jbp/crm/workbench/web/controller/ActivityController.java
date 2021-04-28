package com.jbp.crm.workbench.web.controller;

import com.jbp.crm.settings.domain.User;
import com.jbp.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private UserService userService;

    @RequestMapping("getUserList")
    @ResponseBody
    public List<User> getUserList(){
        List<User> users = userService.getUserList();
        return users;
    }
}
