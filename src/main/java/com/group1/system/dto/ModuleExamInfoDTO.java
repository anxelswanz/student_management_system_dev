package com.group1.system.dto;

import lombok.Data;


/**
 * @author Jingwei Luo
 * @description: Data Transfer Object representing the information of module and exam.
 * @date 2024/5/4 15:00
 * @ProjectName student_management_system_backend
 */
@Data
public class ModuleExamInfoDTO {
    private String moduleId;
    private String moduleName;
    private String examDate;
    private String examSite;
    private String examStartTime;
    private String examEndTime;
    private int examDuration;
    private String examDes;
}
