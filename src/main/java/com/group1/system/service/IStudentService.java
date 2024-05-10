package com.group1.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group1.system.entity.Student;

/**
 * @author Ronghui Zhong
 * @description: Student Service class:
 *  Provides service functions for managing student information within the Student Management System.
 *  It acts as an intermediary between the controller layer and the student data model,
 *  ensuring business rules and validations are applied to the data before it is persisted or returned to the client.
 * @date 2024/3/29 22:42
 * @ProjectName Dyson Student Management System
 **/
public interface IStudentService extends IService<Student> {

}
