package com.jbp.crm.workbench.dao;

import com.jbp.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    Integer save(Activity activity);

    Integer getTotalByCondition(Map<String, Object> map);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    Integer delete(String[] id);
}
