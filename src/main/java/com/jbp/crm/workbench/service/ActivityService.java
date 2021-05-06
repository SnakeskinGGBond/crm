package com.jbp.crm.workbench.service;

import com.jbp.crm.exception.ActivityException;
import com.jbp.crm.vo.PaginationVO;
import com.jbp.crm.workbench.domain.Activity;



public interface ActivityService {
    void save(Activity activity) throws ActivityException;

    PaginationVO<Activity> pageList(Activity activity, Integer pageSizeInt, Integer skipCount);

    void delete(String[] id) throws ActivityException;
}
