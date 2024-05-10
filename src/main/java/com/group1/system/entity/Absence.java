package com.group1.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * @author Ronghui Zhong
 * @description: Absence Entity Class
 * @date 2024/4/28 14:11
 * @ProjectName Dyson Student Management System
 **/
public class Absence implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Absence Id
     */

    private String absenceId;

    /**
     * Student Id
     */
    @TableId("student_id")
    private String studentId;


    /**
     * 0: not decided; 1: approved; 2: not approved.
     */
    private Integer status;

    /**
     * Reason for absence
     */
    private String reason;

    /**
     * The starting time for absence
     */
    private String startTime;

    /**
     * The ending time for absence
     */
    private String endTime;

    /**
     * Staff Id
     */
    private String staffId;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getAbsenceId() {
        return absenceId;
    }

    public void setAbsenceId(String absenceId) {
        this.absenceId = absenceId;
    }
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Absence{" +
                "absenceId='" + absenceId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", staffId='" + staffId + '\'' +
                '}';
    }
}
