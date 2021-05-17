package com.jbp.crm.settings.service.impl;

import com.jbp.crm.settings.dao.DicTypeDao;
import com.jbp.crm.settings.dao.DicValueDao;
import com.jbp.crm.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DicServiceImpl implements DicService {
    @Autowired
    private DicTypeDao dicTypeDao;
    @Autowired
    private DicValueDao dicValueDao;
}
