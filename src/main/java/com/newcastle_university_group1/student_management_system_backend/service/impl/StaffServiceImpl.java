package com.newcastle_university_group1.student_management_system_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.newcastle_university_group1.student_management_system_backend.entity.Staff;
import com.newcastle_university_group1.student_management_system_backend.mapper.StaffMapper;
import com.newcastle_university_group1.student_management_system_backend.service.IStaffService;
import org.springframework.stereotype.Service;

/**
 * @author Ronghui Zhong
 * @description: StaffService Implementation class
 *  This class encapsulates the logic related to student operations, including creating,
 *  updating, deleting, and retrieving staff user records.
 * @date 2024/3/29 21:51
 * @ProjectName Dyson Student Management System
 **/
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements IStaffService {

}
