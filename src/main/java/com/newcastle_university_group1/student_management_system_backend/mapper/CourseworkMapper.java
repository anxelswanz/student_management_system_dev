package com.newcastle_university_group1.student_management_system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newcastle_university_group1.student_management_system_backend.entity.Coursework;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author Ronghui Zhong
 * @description: CourseworkMapper Interface for CRUD
 * @date 2024/4/7 20:44
 * @ProjectName Dyson Student Management System
 **/
@Mapper
public interface CourseworkMapper extends BaseMapper<Coursework> {

}
