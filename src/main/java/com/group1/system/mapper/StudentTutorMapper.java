package com.group1.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.group1.system.entity.StudentTutor;
import org.apache.ibatis.annotations.Mapper;
/**
 * @author Xuezhu Chen
 * @description: StudentTutor Mapper Interface for CRUD
 * @date 2024/4/19 20:10
 * @ProjectName Dyson Student Management System
 **/
@Mapper
public interface StudentTutorMapper extends BaseMapper<StudentTutor> {
}
