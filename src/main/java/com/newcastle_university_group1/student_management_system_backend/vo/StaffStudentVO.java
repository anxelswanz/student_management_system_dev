package com.newcastle_university_group1.student_management_system_backend.vo;

import com.newcastle_university_group1.student_management_system_backend.entity.Module;
import com.newcastle_university_group1.student_management_system_backend.entity.ModuleHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jingwei Luo
 * @description: Represents a composite view object encapsulating various details about a student,
 * the student's modules, and module histories.
 * @date 2024/5/4 15:00
 * @ProjectName Dyson Student Management System
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffStudentVO {

    private String studentId;

    private Integer studentType;

    private String email;

    private String password;

    private String surname;

    private String firstName;

    private String programmeId;

    private Integer studentYear;

    private List<Module> modules;

    private List<ModuleHistory> moduleHistories;

}
