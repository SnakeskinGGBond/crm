package com.jbp.crm.workbench.domain;

public class Activity {
    /**
     * 主键
     */
    private String id;
    /**
     * 市场活动所有者
     * 外键,关联tbl_user表,UUID
     */
    private String owner;
    /**
     * 市场活动名称
     */
    private String name;
    /**
     * 市场活动开始日期
     * 年月日
     */
    private String startDate;
    /**
     * 市场活动结束日期
     * 年月日
     */
    private String endDate;
    /**
     * 市场活动成本
     */
    private String cost;
    /**
     * 市场活动描述
     */
    private String description;
    /**
     * 创建时间
     * 年月日 时分秒
     */
    private String createTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 修改时间
     * 年月日 时分秒
     */
    private String editTime;
    /**
     * 修改人
     */
    private String editBy;


    public Activity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", cost='" + cost + '\'' +
                ", description='" + description + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", editTime='" + editTime + '\'' +
                ", editBy='" + editBy + '\'' +
                '}';
    }
}
