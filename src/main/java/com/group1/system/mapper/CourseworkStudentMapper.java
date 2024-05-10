package com.group1.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.group1.system.entity.CourseworkStudent;
import org.apache.ibatis.annotations.Mapper;
/**
 * @author Xuezhu Chen
 * @description: CourseworkStudentMapper Interface for CRUD
 * @date 2024/4/19 20:00
 * @ProjectName Dyson Student Management System
 **/
@Mapper
public interface CourseworkStudentMapper  extends BaseMapper<CourseworkStudent> {
}
