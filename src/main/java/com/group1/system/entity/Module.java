package com.group1.system.entity;

import java.io.Serializable;

/**
 * @author Ronghui Zhong
 * @description: Module Entity Class
 * @date 2024/4/4 22:10
 * @ProjectName Dyson Student Management System
 **/
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  Programme Id
     */
    private String programmeId;

    /**
     *  Module Id
     */
    private String moduleId;

    /**
     *  Credits
     */
    private Integer credits;

    /**
     *  Module Name
     */
    private String moduleName;

    /**
     *  Date
     */
    private String date;

    /**
     *  Staff Id
     */
    private String staffId;

    /**
     *  Type
     */
    private String type;

    /**
     *  Count
     */
    private Integer count;

    /**
     * 0: Already ended, 1: Ongoing, 2:Upcoming
     */
    private Integer status;

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }
    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Module{" +
            "programmeId=" + programmeId +
            ", moduleId=" + moduleId +
            ", credits=" + credits +
            ", moduleName=" + moduleName +
            ", date=" + date +
            ", staffId=" + staffId +
            ", type=" + type +
            ", count=" + count +
            ", status=" + status +
        "}";
    }
}
