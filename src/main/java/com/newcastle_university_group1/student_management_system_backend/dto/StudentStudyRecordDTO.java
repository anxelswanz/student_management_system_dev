package com.newcastle_university_group1.student_management_system_backend.dto;

import lombok.Data;
/**

 * @author Xuezhu Chen
 * @since 2024-04-20
 * Data Transfer Object (DTO) representing a student's grade history record for a module.
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
