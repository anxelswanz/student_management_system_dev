package com.group1.system.entity;

import java.io.Serializable;

/**
 * @author Ronghui Zhong
 * @description: Coursework Entity Class
 * @date 2024/4/7 20:16
 * @ProjectName Dyson Student Management System
 **/
public class Coursework implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  Module Id
     */
    private String moduleId;

    /**
     *  Coursework Description
     */
    private String courseworkDescription;

    /**
     *  Coursework Id Unique
     */
    private String courseworkId;

    /**
     *  Coursework Name
     */
    private String courseworkName;

    /**
     *  Coursework Release Time
     */
    private String releaseTime;

    /**
     *  Coursework Deadline
     */
    private String deadline;

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
    public String getCourseworkDescription() {
        return courseworkDescription;
    }

    public void setCourseworkDescription(String courseworkDescription) {
        this.courseworkDescription = courseworkDescription;
    }
    public String getCourseworkId() {
        return courseworkId;
    }

    public void setCourseworkId(String courseworkId) {
        this.courseworkId = courseworkId;
    }
    public String getCourseworkName() {
        return courseworkName;
    }

    public void setCourseworkName(String courseworkName) {
        this.courseworkName = courseworkName;
    }
    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }
    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Coursework{" +
            "moduleId=" + moduleId +
            ", courseworkDescription=" + courseworkDescription +
            ", courseworkId=" + courseworkId +
            ", courseworkName=" + courseworkName +
            ", releaseTime=" + releaseTime +
            ", deadline=" + deadline +
        "}";
    }
}
