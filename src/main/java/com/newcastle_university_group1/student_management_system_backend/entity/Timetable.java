package com.newcastle_university_group1.student_management_system_backend.entity;

import java.io.Serializable;

/**
 * @author Ronghui Zhong
 * @since 2024-04-29
 */
public class Timetable implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Module Name
     */
    private String moduleName;

    /**
     * Module ID
     */
    private String moduleId;

    /**
     * Class Location
     */
    private String classLocation;

    /**
     * Programme ID
     */
    private String programmeId;

    /**
     * Module Time: 1 - 5 stands for Monday to Friday, 1-1 stands for Monday morning, 1-2 stands for Monday afternoon, we assume that morning class is from 9 - 11, afternoon class is from 3-5
     */
    private String moduleTime;

    /**
     * Module Year
     */
    private String year;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
    public String getClassLocation() {
        return classLocation;
    }

    public void setClassLocation(String classLocation) {
        this.classLocation = classLocation;
    }
    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }
    public String getModuleTime() {
        return moduleTime;
    }

    public void setModuleTime(String moduleTime) {
        this.moduleTime = moduleTime;
    }
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Timetable{" +
            "moduleName=" + moduleName +
            ", moduleId=" + moduleId +
            ", classLocation=" + classLocation +
            ", programmeId=" + programmeId +
            ", moduleTime=" + moduleTime +
            ", year=" + year +
        "}";
    }
}
