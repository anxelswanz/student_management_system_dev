package com.newcastle_university_group1.student_management_system_backend.dto;

import lombok.Data;

@Data
public class ModuleExamInfoDTO {
    private String moduleId;
    private String moduleName;
    private String examDate;
    private String examSite;
    private String examStartTime;
    private String examEndTime;
    private int examDuration;
}
