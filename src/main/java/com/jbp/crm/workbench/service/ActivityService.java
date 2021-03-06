package com.jbp.crm.workbench.service;

import com.jbp.crm.exception.ActivityException;
import com.jbp.crm.vo.PaginationVO;
import com.jbp.crm.workbench.domain.Activity;
import com.jbp.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;


public interface ActivityService {
    void save(Activity activity) throws ActivityException;

    PaginationVO<Activity> pageList(Activity activity, Integer pageSizeInt, Integer skipCount);

    void delete(String[] id) throws ActivityException;

    Map<String, Object> getUserListAndActivity(String id);

    void update(Activity activity) throws ActivityException;

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String id);

    void removeActivityRemark(String id) throws ActivityException;

    void saveRemark(ActivityRemark ar) throws ActivityException;

    void updateRemark(ActivityRemark ar) throws ActivityException;
}
