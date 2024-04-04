package com.newcastle_university_group1.student_management_system_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newcastle_university_group1.student_management_system_backend.entity.Staff;


/**
 * @author Ronghui Zhong
 * @description: Student Service class:
 *  Provides service functions for managing staff information within the Student Management System.
 *  It acts as an intermediary between the controller layer and the staff data model,
 *  ensuring business rules and validations are applied to the data before it is persisted or returned to the client.
 * @date 2024/3/29 22:44
 * @ProjectName Dyson Student Management System
 **/
public interface IStaffService extends IService<Staff> {

}
