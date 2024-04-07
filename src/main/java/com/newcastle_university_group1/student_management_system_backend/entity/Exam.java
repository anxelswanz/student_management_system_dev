package com.newcastle_university_group1.student_management_system_backend.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

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
     * Student Id
     */
    private String studentId;
    /**
     * Corresponding Module
     */
    private String examModule;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getExamModule() {
        return examModule;
    }

    public void setExamModule(String examModule) {
        this.examModule = examModule;
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
                ", studentId='" + studentId + '\'' +
                ", examModule='" + examModule + '\'' +
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
