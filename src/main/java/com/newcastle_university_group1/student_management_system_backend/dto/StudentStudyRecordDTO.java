package com.newcastle_university_group1.student_management_system_backend.dto;

import lombok.Data;

@Data
public class StudentStudyRecordDTO {
    private String moduleId;
    private String moduleName;
    private String startTime;
    private double courseworkMark;
    private double examMark;

}
