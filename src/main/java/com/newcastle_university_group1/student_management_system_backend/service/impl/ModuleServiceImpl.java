package com.newcastle_university_group1.student_management_system_backend.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newcastle_university_group1.student_management_system_backend.entity.Module;
import com.newcastle_university_group1.student_management_system_backend.mapper.ModuleMapper;
import com.newcastle_university_group1.student_management_system_backend.service.IModuleService;
import org.springframework.stereotype.Service;

/**
 * @author Ronghui Zhong
 * @description: IModuleService Implementation class
 *  This class encapsulates the logic related to module operations
 * @date 2024/4/4 18:00
 * @ProjectName Dyson Student Management System
 **/
@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements IModuleService {

}
