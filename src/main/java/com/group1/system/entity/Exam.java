package com.group1.system.entity;

import java.io.Serializable;

/**
 * @author Ronghui Zhong
 * @description: Exam Entity Class
 * @date 2024/4/7 18:16
 * @ProjectName Dyson Student Management System
 **/
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  Exam Id
     */
    private String examId;

    /**
     * Corresponding Module
     */
    private String moduleId;
    /**
     *  Exam Date
     */
    private String examDate;
    /**
     *  Exam Site
     */
    private String examSite;
    /**
     *  Exam Start Time
     */
    private String examStartTime;
    /**
     *  Exam End Time
     */
    private String examEndTime;
    /**
     *  Exam duration
     */
    private Integer examDuration;
    /**
     *  Exam Name
     */
    private String examName;
    /**
     *  Exam Description
     */
    private String examDesc;

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }



    public String getExamModule() {
        return moduleId;
    }

    public void setExamModule(String examModule) {
        this.moduleId = examModule;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamSite() {
        return examSite;
    }

    public void setExamSite(String examSite) {
        this.examSite = examSite;
    }

    public String getExamStartTime() {
        return examStartTime;
    }

    public void setExamStartTime(String examStartTime) {
        this.examStartTime = examStartTime;
    }

    public String getExamEndTime() {
        return examEndTime;
    }

    public void setExamEndTime(String examEndTime) {
        this.examEndTime = examEndTime;
    }

    public Integer getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(Integer examDuration) {
        this.examDuration = examDuration;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamDesc() {
        return examDesc;
    }

    public void setExamDesc(String examDesc) {
        this.examDesc = examDesc;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "examId='" + examId + '\'' +
                ", examModule='" + moduleId + '\'' +
                ", examDate='" + examDate + '\'' +
                ", examSite='" + examSite + '\'' +
                ", examStartTime='" + examStartTime + '\'' +
                ", examEndTime='" + examEndTime + '\'' +
                ", examDuration=" + examDuration +
                ", examName='" + examName + '\'' +
                ", examDesc='" + examDesc + '\'' +
                '}';
    }
}
