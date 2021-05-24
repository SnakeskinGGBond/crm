package com.jbp.crm.settings.dao;

import com.jbp.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
