package com.newcastle_university_group1.student_management_system_backend.entity;



/**
 * @author Ronghui Zhong
 * @description: Exam Entity Class
 * @date 2024/4/29 18:16
 * @ProjectName Dyson Student Management System
 **/
public class StudentTutor {
    private String studentId;
    private String tutorId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    @Override
    public String toString() {
        return "StudentTutor{" +
                "studentId='" + studentId + '\'' +
                ", tutorId='" + tutorId + '\'' +
                '}';
    }
}
