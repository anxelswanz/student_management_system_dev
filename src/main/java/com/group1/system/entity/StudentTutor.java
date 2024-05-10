package com.group1.system.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ronghui Zhong
 * @description: Exam Entity Class
 * @date 2024/4/29 18:16
 * @ProjectName Dyson Student Management System
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentTutor {

    @TableId("student_id")
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
