package com.newcastle_university_group1.student_management_system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newcastle_university_group1.student_management_system_backend.entity.Module;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Ronghui Zhong
 * @description: ModuleMapper Interface for CRUD
 * @date 2024/4/4 23:00
 * @ProjectName Dyson Student Management System
 **/
@Mapper
public interface ModuleMapper extends BaseMapper<Module> {

}
