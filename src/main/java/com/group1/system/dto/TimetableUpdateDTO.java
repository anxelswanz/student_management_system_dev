package com.group1.system.dto;

import lombok.Data;

/**
 * @author Jingwei Luo
 * @description: Data Transfer Object used for updating the timetable information of a module within a programme.
 * @date 2024/5/4 15:00
 * @ProjectName student_management_system_backend
 */
@Data
public class TimetableUpdateDTO {
    private String id;
    private String moduleName;
    private String moduleId;
    private String classLocation;
    private String prorgammeId;
    // Module Time: 1 - 5 stands for Monday to Friday,
    // 1-1 stands for Monday morning,
    // 1-2 stands for Monday afternoon,
    // we assume that morning class is from 9 - 11, afternoon class is from 3-5
    private String moduleTime;
    private String year;
}
