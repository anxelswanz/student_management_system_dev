package com.newcastle_university_group1.student_management_system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newcastle_university_group1.student_management_system_backend.entity.ExamStudent;
import org.apache.ibatis.annotations.Mapper;
/**
 * @author Xuezhu Chen
 * @description: ExamStudentMapper Interface for CRUD
 * @date 2024/4/19 20:05
 * @ProjectName Dyson Student Management System
 **/
@Mapper
public interface ExamStudentMapper extends BaseMapper<ExamStudent> {
}
