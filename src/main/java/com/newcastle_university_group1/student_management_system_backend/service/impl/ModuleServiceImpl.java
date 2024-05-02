package com.newcastle_university_group1.student_management_system_backend.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newcastle_university_group1.student_management_system_backend.dto.StudentStudyRecordDTO;
import com.newcastle_university_group1.student_management_system_backend.entity.Module;
import com.newcastle_university_group1.student_management_system_backend.mapper.ModuleMapper;
import com.newcastle_university_group1.student_management_system_backend.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ronghui Zhong
 * @description: IModuleService Implementation class
 *  This class encapsulates the logic related to module operations
 * @date 2024/4/4 18:00
 * @ProjectName Dyson Student Management System
 **/
@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements IModuleService {
    @Autowired
    private ModuleMapper moduleMapper;


    /**
     * Authorization: Student
     * @author Xuezhu Chen
     *
     * This method retrieves the study record for a student with pagination from the database using the provided student ID
     * and pagination information. It returns an {@code IPage<StudentStudyRecordDTO>} object containing the study record
     * for the student with pagination.
     *
     * @param studentId The unique identifier of the student.
     * @param page The pagination information including current page and page size.
     * @return An {@code IPage<StudentStudyRecordDTO>} object containing the study record for the student with pagination.
     */
    @Override
    public IPage<StudentStudyRecordDTO> getStudyRecordWithPagination(String studentId, Page<StudentStudyRecordDTO> page) {
        return moduleMapper.getStudyRecordWithPagination(studentId,page);

    }

}
