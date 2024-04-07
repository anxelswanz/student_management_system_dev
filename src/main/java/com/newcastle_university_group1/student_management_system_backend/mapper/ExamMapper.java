package com.newcastle_university_group1.student_management_system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newcastle_university_group1.student_management_system_backend.entity.Exam;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Ronghui Zhong
 * @description: ExamMapper Interface for CRUD
 * @date 2024/4/7 18:30
 * @ProjectName Dyson Student Management System
 **/
@Mapper
public interface ExamMapper extends BaseMapper<Exam> {

}
