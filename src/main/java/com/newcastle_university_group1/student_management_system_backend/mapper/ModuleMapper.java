package com.newcastle_university_group1.student_management_system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newcastle_university_group1.student_management_system_backend.dto.StudentStudyRecordDTO;
import com.newcastle_university_group1.student_management_system_backend.entity.Module;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Ronghui Zhong
 * @description: ModuleMapper Interface for CRUD
 * @date 2024/4/4 23:00
 * @ProjectName Dyson Student Management System
 **/
@Mapper
public interface ModuleMapper extends BaseMapper<Module> {
    /**module start time and end time
     *
     * @param studentId
     * @return
     */
    @Select("SELECT date , module_name FROM module WHERE programme_id = (SELECT programme_id FROM student WHERE student_id = #{studentId})")
    List<Module> getModuleTime(String studentId);

    /**module exam information
     *
     * @param studentId
     * @param page
     * @return
     */
    @Select("SELECT m.module_id AS moduleId, m.module_name AS moduleName, m.date AS startTime, cs.mark AS courseworkMark, es.mark AS examMark " +
            "FROM student s " +
            "JOIN programme p ON s.programme_id = p.programme_id " +
            "JOIN module m ON p.programme_id = m.programme_id " +
            "JOIN coursework_student cs ON s.student_id = cs.student_id " +
            "JOIN exam_student es ON s.student_id = es.student_id " +
            "WHERE s.student_id = #{studentId} " +
            "LIMIT #{page.current}, #{page.size}")
    IPage<StudentStudyRecordDTO> getStudyRecordWithPagination(String studentId, Page<StudentStudyRecordDTO> page);


}
