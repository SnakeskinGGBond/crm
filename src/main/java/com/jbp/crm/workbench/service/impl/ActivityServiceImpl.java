package com.jbp.crm.workbench.service.impl;

import com.jbp.crm.exception.*;
import com.jbp.crm.settings.dao.UserDao;
import com.jbp.crm.settings.domain.User;
import com.jbp.crm.vo.PaginationVO;
import com.jbp.crm.workbench.dao.ActivityDao;
import com.jbp.crm.workbench.dao.ActivityRemarkDao;
import com.jbp.crm.workbench.domain.Activity;
import com.jbp.crm.workbench.domain.ActivityRemark;
import com.jbp.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private ActivityRemarkDao activityRemarkDao;
    @Autowired
    private UserDao userDao;

    /**
     * 市场活动保存方法
     *
     * @param activity 要保存的市场活动的信息
     * @throws ActivityException 起始时间晚于结束时间异常
     */
    @Override
    public void save(Activity activity) throws ActivityException {
        if (activity.getStartDate().compareTo(activity.getEndDate()) > 0) {
            throw new ActivityAddException("添加失败,起始时间晚于结束时间");
        }
        Integer res = activityDao.save(activity);
        if (res != 1) {
            throw new ActivityAddException("添加失败");
        }
    }

    /**
     * 查询市场活动列表方法
     *
     * @param activity    查询条件
     * @param pageSizeInt 每页展示条数
     * @param skipCount   略过的记录数
     * @return 封装的PaginationVO<Activity>类,包括总条数和每条的信息
     */
    @Override
    public PaginationVO<Activity> pageList(Activity activity, Integer pageSizeInt, Integer skipCount) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", activity.getName());
        map.put("owner", activity.getOwner());
        map.put("startDate", activity.getStartDate());
        map.put("endDate", activity.getEndDate());
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSizeInt);
        //取得total
        Integer total = activityDao.getTotalByCondition(map);
        //取得dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);
        //封装vo
        PaginationVO<Activity> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    /**
     * 市场活动删除方法
     * @param id 需要删除的市场活动的id,是个数组,支持批量删除
     * @throws ActivityException 市场活动删除的相关错误
     */
    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false,
            rollbackFor = {ActivityDeleteException.class, ActivityException.class}
    )
    public void delete(String[] id) throws ActivityException {
        //查询出需要删除的备注的数量
        Integer count1 = activityRemarkDao.getCountByAids(id);
        //删除备注,返回收到影响的条数(实际删除数量)
        Integer count2 = activityRemarkDao.deleteByAids(id);

        if (count1 != count2) {
            throw new ActivityDeleteException("删除失败!");
        }
        //删除市场活动
        Integer count3 = activityDao.delete(id);
        if (count3 != id.length) {
            throw new ActivityDeleteException("删除失败");
        }
    }

    /**
     * 获取用户列表和市场活动信息方法
     * @param id 市场活动信息的id
     * @return 用户列表和市场活动信息
     */
    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        //取userList
        List<User> userList = userDao.getUserList();
        //取activity
        Activity activity = activityDao.getById(id);
        //返回map
        Map<String,Object> map = new HashMap<>();
        map.put("userList",userList);
        map.put("activity",activity);
        return map;
    }

    /**
     * 更新市场活动信息方法
     * @param activity 需要更新的市场活动信息
     */
    @Override
    public void update(Activity activity) throws ActivityException{
        if (activity.getStartDate().compareTo(activity.getEndDate()) > 0) {
            System.out.println("更新失败,起始时间晚于结束时间");
            throw new ActivityUpdateException("更新失败,起始时间晚于结束时间");
        }
        Integer res = activityDao.update(activity);
        if (res != 1) {
            throw new ActivityUpdateException("更新失败");
        }
    }

    @Override
    public Activity detail(String id) {
        Activity activity = activityDao.detail(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String id) {
        List<ActivityRemark> arList = activityRemarkDao.getRemarkListByAid(id);
        return arList;
    }

    @Override
    public void removeActivityRemark(String id) throws ActivityException{
        Integer res = activityRemarkDao.removeActivityRemark(id);
        if (res != 1){
            throw new ActivityRemarkDeleteException("删除失败!");
        }
    }

    @Override
    public void saveRemark(ActivityRemark activityRemark) throws ActivityException{
        Integer res = activityRemarkDao.saveRemark(activityRemark);
        if (res != 1){
            throw new ActivityRemarkSaveException("添加失败!");
        }
    }

    @Override
    public void updateRemark(ActivityRemark ar) throws ActivityException{
        Integer res = activityRemarkDao.updateRemark(ar);
        if (res != 1){
            throw new ActivityRemarkUpdateException("更新失败!");
        }
    }


}
