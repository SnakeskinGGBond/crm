package com.jbp.crm.workbench.web.controller;

import com.jbp.crm.exception.ActivityException;
import com.jbp.crm.settings.domain.User;
import com.jbp.crm.settings.service.UserService;
import com.jbp.crm.utils.DateTimeUtil;
import com.jbp.crm.utils.UUIDUtil;
import com.jbp.crm.vo.PaginationVO;
import com.jbp.crm.workbench.domain.Activity;
import com.jbp.crm.workbench.domain.ActivityRemark;
import com.jbp.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

    /**
     * 获取用户列表
     * @return 用户列表
     */
    @RequestMapping("getUserList")
    @ResponseBody
    public List<User> getUserList() {
        List<User> users = userService.getUserList();
        return users;
    }

    /**
     * 添加市场活动信息
     * @param activity 前端传入的市场活动信息对象
     * @param request 用来获取session中的用户信息
     * @return 添加成功信息
     * @throws ActivityException 市场活动添加相关异常
     */
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

    /**
     * 获取需要修改的市场活动信息
     * @param id 需要修改的市场活动信息的id
     * @return 用户信息列表和需要修改的市场活动信息(Activity)对象
     */
    @RequestMapping("getUserListAndActivity")
    @ResponseBody
    public Map<String,Object> getUserListAndActivity(String id){
        System.out.println(id);
        Map<String,Object> map = activityService.getUserListAndActivity(id);
        List<User> userList =(List<User>) map.get("userList");
        for (User user : userList) {
            System.out.println(user);
        }
        return map;
    }

    /**
     * 更新市场活动信息
     * @param activity 需要更新的市场活动信息
     * @param request 获取session中保存的登录对象
     * @return 更新成功信息
     * @throws ActivityException 更新相关的异常
     */
    @RequestMapping("update")
    @ResponseBody
    public Map<String,Object> update(Activity activity,HttpServletRequest request) throws ActivityException {
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();
        activity.setEditBy(editBy);
        activity.setEditTime(editTime);
        activityService.update(activity);
        Map map = new HashMap();
        map.put("success", true);
        return map;
    }

    /**
     * 展示市场活动信息详情
     * @param id 展示的市场活动的id
     * @return 通过id查询到的市场活动对象,请求转发的路径
     */
    @RequestMapping("detail")
    public ModelAndView detail(String id){
        Activity activity = activityService.detail(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("activity",activity);
        mv.setViewName("forward:/workbench/activity/detail.jsp");
        return mv;
    }

    /**
     * 通过id获取备注列表
     * @param id ActivityId
     * @return ActivityId所拥有的备注列表
     */
    @RequestMapping("getRemarkListByAid")
    @ResponseBody
    public List<ActivityRemark> getRemarkListByAid(String id){
        List<ActivityRemark> arList = activityService.getRemarkListByAid(id);
        return arList;
    }

    /**
     * 删除市场活动备注
     * @param id 需要删除的市场活动备注的id
     * @return 删除成功
     * @throws ActivityException 市场活动备注相关异常
     */
    @RequestMapping("removeActivityRemark")
    @ResponseBody
    public Map<String,Object> removeActivityRemark(String id) throws ActivityException{
        System.out.println(id);
        activityService.removeActivityRemark(id);
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        return map;
    }

    @RequestMapping("saveRemark")
    @ResponseBody
    public Map<String,Object> saveRemark(String noteContent,String activityId,HttpServletRequest request) throws ActivityException {
        System.out.println("执行添加备注操作");
        Map<String,Object> map = new HashMap<>();
        String id = UUIDUtil.getUUID();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String editFlag = "0";

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setActivityId(activityId);
        ar.setCreateBy(createBy);
        ar.setCreateTime(createTime);
        ar.setEditFlag(editFlag);

        activityService.saveRemark(ar);
        map.put("success",true);
        map.put("activityRemark",ar);

        return map;
    }

    @RequestMapping("updateRemark")
    @ResponseBody
    public Map<String,Object> updateRemark(String id,String noteContent,HttpServletRequest request) throws ActivityException {
        System.out.println("执行修改备注操作");
        Map<String ,Object> map = new HashMap<>();
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setEditBy(editBy);
        ar.setEditTime(editTime);
        ar.setEditFlag("1");

        activityService.updateRemark(ar);

        map.put("success",true);
        map.put("activityRemark",ar);
        return map;
    }
}

