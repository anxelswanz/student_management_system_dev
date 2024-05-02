package com.newcastle_university_group1.student_management_system_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newcastle_university_group1.student_management_system_backend.dto.ModuleExamInfoDTO;
import com.newcastle_university_group1.student_management_system_backend.entity.Exam;

import java.util.List;


/**
 * @author Ronghui Zhong
 * @description: Exam Service Interface
 * @date 2024/4/7 19:00
 * @ProjectName Dyson Student Management System
 **/
public interface IExamService extends IService<Exam> {
    /**
     * Authorization: Student
     * @author Xuezhu Chen
     *
     * This method retrieves module exam information for a student from the database using the provided student ID.
     *
     * @param studentId The unique identifier of the student.
     * @return A list of {@code ModuleExamInfoDTO} objects containing module exam information for the student.
     */
    List<ModuleExamInfoDTO> getModuleExamInfo(String studentId);
}
