package com.jbp.crm.workbench.web.controller;

import com.jbp.crm.exception.ActivityException;
import com.jbp.crm.settings.domain.User;
import com.jbp.crm.settings.service.UserService;
import com.jbp.crm.utils.DateTimeUtil;
import com.jbp.crm.utils.UUIDUtil;
import com.jbp.crm.vo.PaginationVO;
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
    public List<User> getUserList() {
        List<User> users = userService.getUserList();
        return users;
    }

    @RequestMapping("save")
    @ResponseBody
    public Map<String, Object> save(Activity activity, HttpServletRequest request) throws ActivityException {
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        activity.setCreateBy(((User) request.getSession().getAttribute("user")).getName());
        activityService.save(activity);
        Map map = new HashMap();
        map.put("success", true);
        return map;
    }

    /**
     * 查询市场活动信息列表
     *
     * @param activity
     * @param pageNo
     * @param pageSize 每页展示的记录条数
     * @return
     */
    @RequestMapping("pageList")
    @ResponseBody
    public PaginationVO<Activity> pageList(Activity activity, String pageNo, String pageSize) {
        Integer pageNoInt = Integer.valueOf(pageNo);
        Integer pageSizeInt = Integer.valueOf(pageSize);
        //计算出略过的记录数
        Integer skipCount = (pageNoInt - 1) * pageSizeInt;
        PaginationVO<Activity> vo = activityService.pageList(activity, pageSizeInt, skipCount);
        Map<String, Object> map = new HashMap<>();
        map.put("total", vo.getTotal());
        map.put("dataList", vo.getDataList());
        return vo;
    }

    /**
     * 删除市场活动信息
     * @param id workbench/activity/index.jsp传来的选中的市场活动信息的id
     * @return 删除成功
     * @throws ActivityException 市场活动信息操作的相关错误
     */
    @RequestMapping("delete")
    @ResponseBody
    public Map<String,Object> delete(String[] id) throws ActivityException {
        activityService.delete(id);
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        return map;
    }

}
