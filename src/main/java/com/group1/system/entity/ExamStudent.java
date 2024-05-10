package com.group1.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;


/**
 * @author Xuezhu Chen
 * @description: ExamStudent Entity Class
 * @Modified by Ronghui Zhong
 * @since 2024-04-30
 */
public class ExamStudent {
    @TableId
    private String examId;
    @TableField(value = "student_id")
    private String studentId;
    private double mark;
    private String examDate;
    private boolean ifAttended;

    private String moduleId;


    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

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

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public boolean isIfAttended() {
        return ifAttended;
    }

    public void setIfAttended(boolean ifAttended) {
        this.ifAttended = ifAttended;
    }

    @Override
    public String toString() {
        return "ExamStudent{" +
                "examId='" + examId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", mark=" + mark +
                ", examDate='" + examDate + '\'' +
                ", ifAttended=" + ifAttended +
                ", moduleId='" + moduleId + '\'' +
                '}';
    }
}
