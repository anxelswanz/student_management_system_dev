package com.newcastle_university_group1.student_management_system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newcastle_university_group1.student_management_system_backend.dto.ModuleExamInfoDTO;
import com.newcastle_university_group1.student_management_system_backend.entity.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Ronghui Zhong
 * @description: ExamMapper Interface for CRUD
 * @date 2024/4/7 18:30
 * @ProjectName Dyson Student Management System
 **/
@Mapper
public interface ExamMapper extends BaseMapper<Exam> {
    /**studyRecord
     *
     * @param studentId
     * @return
     */
    @Select("SELECT m.module_id AS moduleId, m.module_name AS moduleName,e.exam_date AS examDate, e.exam_site AS examSite, e.exam_start_time AS examStartTime, e.exam_end_time AS examEndTime, e.exam_duration AS examDuration " +
            "FROM student s " +
            "JOIN programme p ON s.programme_id = p.programme_id " +
            "JOIN module m ON p.programme_id = m.programme_id " +
            "JOIN exam e ON m.module_id = e.exam_module " +
            "WHERE s.student_id = #{studentId} " )
    List<ModuleExamInfoDTO> getModuleExamInfo(String studentId);
}
