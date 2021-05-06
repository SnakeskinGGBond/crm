package com.jbp.crm.workbench.dao;

public interface ActivityRemarkDao {
    Integer getCountByAids(String[] id);

    Integer deleteByAids(String[] id);
}
