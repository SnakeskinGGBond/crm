package com.jbp.crm.workbench.service.impl;

import com.jbp.crm.workbench.dao.ClueDao;
import com.jbp.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueDao clueDao;
}
