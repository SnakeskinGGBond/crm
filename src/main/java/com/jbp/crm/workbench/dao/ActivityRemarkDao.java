package com.jbp.crm.workbench.dao;

import com.jbp.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    Integer getCountByAids(String[] id);

    Integer deleteByAids(String[] id);

    List<ActivityRemark> getRemarkListByAid(String id);

    Integer removeActivityRemark(String id);

    Integer saveRemark(ActivityRemark activityRemark);

    Integer updateRemark(ActivityRemark ar);
}
