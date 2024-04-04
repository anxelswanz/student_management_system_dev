package com.newcastle_university_group1.student_management_system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newcastle_university_group1.student_management_system_backend.entity.Staff;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author Ronghui Zhong
 * @description: StaffMapper Interface for CRUD
 * @date 2024/3/29 21:55
 * @ProjectName Dyson Student Management System
 **/
@Mapper
public interface StaffMapper extends BaseMapper<Staff> {

}
