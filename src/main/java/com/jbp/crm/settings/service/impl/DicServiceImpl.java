package com.jbp.crm.settings.service.impl;

import com.jbp.crm.settings.dao.DicTypeDao;
import com.jbp.crm.settings.dao.DicValueDao;
import com.jbp.crm.settings.domain.DicType;
import com.jbp.crm.settings.domain.DicValue;
import com.jbp.crm.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {
    @Autowired
    private DicTypeDao dicTypeDao;
    @Autowired
    private DicValueDao dicValueDao;

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map = new HashMap<>();
        //将字典类型取出
        List<DicType> dtList = dicTypeDao.getTypeList();
        //将字典列表遍历
        for (DicType dt : dtList) {
            String code = dt.getCode();

            //根据每个字典类型来取得字典值列表
            List<DicValue> dvList = dicValueDao.getListByCode(code);
            map.put(code + "List", dvList);
        }
        return map;
    }
}
