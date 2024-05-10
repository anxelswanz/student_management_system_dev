package com.group1.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.group1.system.entity.ExamStudent;
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
