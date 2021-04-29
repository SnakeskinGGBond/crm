package com.jbp.crm.workbench.web.controller;

import com.jbp.crm.exception.ActivityException;
import com.jbp.crm.settings.domain.User;
import com.jbp.crm.settings.service.UserService;
import com.jbp.crm.utils.DateTimeUtil;
import com.jbp.crm.utils.UUIDUtil;
import com.jbp.crm.workbench.domain.Activity;
import com.jbp.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    @RequestMapping("getUserList")
    @ResponseBody
    public List<User> getUserList(){
        List<User> users = userService.getUserList();
        return users;
    }

    @RequestMapping("save")
    @ResponseBody
    public Map<String,Object> save(Activity activity, HttpServletRequest request) throws ActivityException {
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        activity.setCreateBy(((User)request.getSession().getAttribute("user")).getName());

        activityService.save(activity);
        Map map = new HashMap();
        map.put("success",true);
        return map;
    }

}
