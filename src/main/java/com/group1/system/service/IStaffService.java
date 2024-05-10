package com.group1.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group1.system.entity.Staff;


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
