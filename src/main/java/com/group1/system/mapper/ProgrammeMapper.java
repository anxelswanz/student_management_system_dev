package com.group1.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.group1.system.entity.Programme;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author Ronghui Zhong
 * @description: ProgrammeMapper Interface for CRUD
 * @date 2024/4/4 23:25
 * @ProjectName Dyson Student Management System
 **/
@Mapper
public interface ProgrammeMapper extends BaseMapper<Programme> {

}
