package com.group1.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.group1.system.entity.Timetable;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Ronghui Zhong
 * @description: Timetable Mapper Interface for CRUD
 * @date 2024/5/1 21:12
 * @ProjectName Dyson Student Management System
 **/
@Mapper
public interface TimetableMapper extends BaseMapper<Timetable> {

}
