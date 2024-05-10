package com.group1.system.dto;

import lombok.Data;
/**
 * @author Xuezhu Chen
 * @description: Data Transfer Object (DTO) representing a student's grade history record for a module.
 * @date 2024/4/2 17:00
 * @ProjectName student_management_system_backend
 */
@Data
public class StudentStudyRecordDTO {
    // The ID of the module.
    private String moduleId;
    // The name of the module.
    private String moduleName;
    // The coursework mark obtained by the student.
    private double courseworkMark;
    // The exam mark obtained by the student.
    private double examMark;
    // The total mark obtained by the student for the module.
    private double totalMark;
}
