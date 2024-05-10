package com.group1.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * @author Ronghui Zhong
 * @description: Module History Entity Class
 * @date 2024/4/30 19:31
 * @ProjectName Dyson Student Management System
 **/
@TableName("module_history")
public class ModuleHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Student ID
     */
    private String studentId;

    /**
     * Module ID
     */
    private String moduleId;

    /**
     * Coursework Mark
     */
    private Double courseworkMark;

    /**
     * Exam Mark
     */
    private Double examMark;

    /**
     * Total Mark
     */
    private Double totalMark;

    /**
     * Module Name
     */
    private String moduleName;

    /**
     * Module Result: If final mark > 60 then pass, if final mark < 60 then fail, the final mark is null then N/A (0:fail / 1: pass / null: N/A)
     */
    private Integer result;

    /**
     * Credits
     */
    private Integer credits;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
    public Double getCourseworkMark() {
        return courseworkMark;
    }

    public void setCourseworkMark(Double courseworkMark) {
        this.courseworkMark = courseworkMark;
    }
    public Double getExamMark() {
        return examMark;
    }

    public void setExamMark(Double examMark) {
        this.examMark = examMark;
    }
    public Double getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(Double totalMark) {
        this.totalMark = totalMark;
    }
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "ModuleHistory{" +
                "studentId='" + studentId + '\'' +
                ", moduleId='" + moduleId + '\'' +
                ", courseworkMark=" + courseworkMark +
                ", examMark=" + examMark +
                ", totalMark=" + totalMark +
                ", moduleName='" + moduleName + '\'' +
                ", result=" + result +
                ", credits=" + credits +
                '}';
    }
}
