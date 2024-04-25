package com.newcastle_university_group1.student_management_system_backend.entity;

import lombok.Data;

@Data
public class ExamStudent {
    private String examId;
    private String studentId;
    private double mark;
    private String examDate;
    private boolean ifAttended;

}
