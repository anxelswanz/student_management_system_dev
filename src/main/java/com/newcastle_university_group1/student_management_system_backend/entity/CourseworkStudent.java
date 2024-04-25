package com.newcastle_university_group1.student_management_system_backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class CourseworkStudent {
    private String studentId;
    private String courseworkId;
    @TableField(exist = false)
    private double mark;
    private String deadline;
    private boolean ifExtended;
    private boolean ifSubmitted;
    private String moduleId;
}
