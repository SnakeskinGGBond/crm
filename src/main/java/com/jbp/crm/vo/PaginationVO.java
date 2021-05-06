package com.jbp.crm.vo;

import java.util.List;

/**
 * 分页查询vo
 * 分页查询,每个模块都有,所以选择使用一个通用vo
 * @param <T>
 */
public class PaginationVO<T> {
    private Integer total;
    private List<T> dataList;

    public PaginationVO() {
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "PaginationVO{" +
                "total=" + total +
                ", dataList=" + dataList +
                '}';
    }
}
