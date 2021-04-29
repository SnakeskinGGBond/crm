package com.jbp.crm.workbench.service.impl;

import com.jbp.crm.exception.ActivityAddException;
import com.jbp.crm.exception.ActivityException;
import com.jbp.crm.workbench.dao.ActivityDao;
import com.jbp.crm.workbench.domain.Activity;
import com.jbp.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityDao activityDao;

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
}
