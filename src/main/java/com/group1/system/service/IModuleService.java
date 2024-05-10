package com.group1.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.group1.system.dto.StudentStudyRecordDTO;
import com.group1.system.entity.Module;



/**
 * @author Ronghui Zhong
 * @description: Module Service Interface
 * @date 2024/4/5 19:55
 * @ProjectName Dyson Student Management System
 **/
public interface IModuleService extends IService<Module> {


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
    IPage<StudentStudyRecordDTO> getStudyRecordWithPagination(String studentId, Page<StudentStudyRecordDTO> page);
}
