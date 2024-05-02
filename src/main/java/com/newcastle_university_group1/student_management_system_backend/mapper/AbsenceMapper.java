package com.newcastle_university_group1.student_management_system_backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newcastle_university_group1.student_management_system_backend.entity.Absence;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**

 * @author Ronghui Zhong
 * @since 2024-04-29
 */
@Mapper
@Repository
public interface AbsenceMapper extends BaseMapper<Absence> {

}
