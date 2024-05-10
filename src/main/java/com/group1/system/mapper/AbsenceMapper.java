package com.group1.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.group1.system.entity.Absence;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author Ronghui Zhong
 * @description: Absence Interface for CRUD
 * @date 2024/4/19 21:01
 * @ProjectName Dyson Student Management System
 **/
@Mapper
@Repository
public interface AbsenceMapper extends BaseMapper<Absence> {

}
