package com.group1.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group1.system.entity.CourseworkStudent;
/**
 * @author Xuezhu Chen
 * @description: CoruseworkStudent Service Interface:
 * Service interface for managing coursework information related to students within the Student Management System.
 * It acts as an intermediary between the controller layer and the coursework data model,
 * ensuring business rules and validations are applied to the data before it is persisted or returned to the client.
 * @date 2024/4/19 20:00
 * @ProjectName Dyson Student Management System
 **/
public interface ICourseworkStudentService extends IService<CourseworkStudent> {
}
