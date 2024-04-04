package com.newcastle_university_group1.student_management_system_backend.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newcastle_university_group1.student_management_system_backend.entity.Student;
import com.newcastle_university_group1.student_management_system_backend.mapper.StudentMapper;
import com.newcastle_university_group1.student_management_system_backend.service.IStudentService;
import org.springframework.stereotype.Service;

/**
 * @author Ronghui Zhong
 * @description: Student Service Implementation class
 *  This class encapsulates the logic related to student operations, including creating,
 *  updating, deleting, and retrieving student user records.
 * @date 2024/3/29 22:30
 * @ProjectName Dyson Student Management System
 **/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
