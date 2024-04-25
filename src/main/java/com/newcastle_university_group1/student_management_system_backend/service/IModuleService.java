package com.newcastle_university_group1.student_management_system_backend.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newcastle_university_group1.student_management_system_backend.dto.StudentStudyRecordDTO;
import com.newcastle_university_group1.student_management_system_backend.entity.Module;

import java.util.List;

/**
 * @author Ronghui Zhong
 * @description: Module Service Interface
 * @date 2024/4/5 19:55
 * @ProjectName Dyson Student Management System
 **/
public interface IModuleService extends IService<Module> {
    List<Module> getModuleTime(String studentId);


    IPage<StudentStudyRecordDTO> getStudyRecordWithPagination(String studentId, Page<StudentStudyRecordDTO> page);
}
