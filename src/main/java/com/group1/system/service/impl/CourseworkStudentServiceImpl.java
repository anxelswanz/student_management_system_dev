package com.group1.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group1.system.entity.CourseworkStudent;
import com.group1.system.mapper.CourseworkStudentMapper;
import com.group1.system.service.ICourseworkStudentService;
import org.springframework.stereotype.Service;


/**
 * @author Ronghui Zhong
 * @description: ICourseworkStudentService Implementation class
 * This class encapsulates the logic related to module operations
 * @date 2024/4/8 20:12
 * @ProjectName Dyson Student Management System
 **/
@Service
public class CourseworkStudentServiceImpl extends ServiceImpl<CourseworkStudentMapper, CourseworkStudent> implements ICourseworkStudentService {
}
