package com.group1.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group1.system.entity.Student;
import com.group1.system.mapper.StudentMapper;
import com.group1.system.service.IStudentService;

import org.springframework.stereotype.Service;

/**
 * @author Ronghui Zhong
 * @description: IStudent Service Implementation class
 *  This class encapsulates the logic related to student operations, including creating,
 *  updating, deleting, and retrieving student user records.
 * @date 2024/3/29 22:30
 * @ProjectName Dyson Student Management System
 **/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
