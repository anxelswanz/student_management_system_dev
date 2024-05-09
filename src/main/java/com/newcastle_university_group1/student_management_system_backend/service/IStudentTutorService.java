package com.newcastle_university_group1.student_management_system_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newcastle_university_group1.student_management_system_backend.entity.StudentTutor;
/**
 * @author Xuezhu Chen
 * @description: StudentTutor Service Interface:
 * Service interface for managing tutor information related to students within the Student Management System.
 * It acts as a bridge between the controller layer and the student-tutor data model,
 * ensuring that business rules and validations are applied to the data before it is persisted or returned to the client.
 * @date 2024/4/19 20:10
 * @ProjectName Dyson Student Management System
 **/
public interface IStudentTutorService extends IService<StudentTutor> {
}
