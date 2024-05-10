package com.group1.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * @author Ronghui Zhong
 * @description: Student Entity Class
 * @date 2024/3/29 21:51
 * @ProjectName Dyson Student Management System
 **/
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Student ID
     */
    @TableId("student_id")
    private String studentId;

    /**
     * Student Type ( 0: Undegraduate, 1: Postgraduate )
     */
    private Integer studentType;

    /**
     * Student Email
     */
    private String email;

    /**
     * Student Password
     */
    private String password;

    /**
     * Student surname
     */
    private String surname;

    /**
     * Studenet firstname
     */
    private String firstName;

    /**
     * The programme student register
     */
    private String programmeId;

    /**
     * The academic year student is in
     */
    private Integer studentYear;

    /**
     * Programme Status
     */

    private Integer programmeStatus;

    public Integer getProgrammeStatus() {
        return programmeStatus;
    }

    public void setProgrammeStatus(Integer programmeStatus) {
        this.programmeStatus = programmeStatus;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public Integer getStudentType() {
        return studentType;
    }

    public void setStudentType(Integer studentType) {
        this.studentType = studentType;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }


    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }
    public Integer getStudentYear() {
        return studentYear;
    }

    public void setStudentYear(Integer studentYear) {
        this.studentYear = studentYear;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getProgrammeId() {
        return programmeId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", studentType=" + studentType +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", surname='" + surname + '\'' +
                ", firstName='" + firstName + '\'' +
                ", programmeId='" + programmeId + '\'' +
                ", studentYear=" + studentYear +
                ", programmeStatus=" + programmeStatus +
                '}';
    }
}
