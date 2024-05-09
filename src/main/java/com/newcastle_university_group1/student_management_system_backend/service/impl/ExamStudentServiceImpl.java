package com.newcastle_university_group1.student_management_system_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newcastle_university_group1.student_management_system_backend.entity.ExamStudent;
import com.newcastle_university_group1.student_management_system_backend.mapper.ExamStudentMapper;
import com.newcastle_university_group1.student_management_system_backend.service.IExamStudentService;
import org.springframework.stereotype.Service;
/**
 * @author Xuezhu Chen
 * @description: IExamStudent Service Implementation class
 *  This class encapsulates the logic related to module operations
 *  updating, deleting, and retrieving student user records.
 * @date 2024/4/19 20:05
 * @ProjectName Dyson Student Management System
 **/
@Service
public class ExamStudentServiceImpl extends ServiceImpl<ExamStudentMapper, ExamStudent> implements IExamStudentService {
}
