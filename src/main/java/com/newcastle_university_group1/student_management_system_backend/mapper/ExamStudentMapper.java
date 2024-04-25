package com.newcastle_university_group1.student_management_system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newcastle_university_group1.student_management_system_backend.entity.ExamStudent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExamStudentMapper extends BaseMapper<ExamStudent> {
}
