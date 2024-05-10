package com.group1.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.group1.system.dto.ModuleExamInfoDTO;
import com.group1.system.entity.Exam;
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
    /**
     * Authorization: Student
     * @author Xuezhu Chen
     *
     * This method retrieves information about module exams for a student from the database. It executes a SQL query
     * to fetch data including module ID, module name, exam date, exam site, exam start time, exam end time,
     * exam duration, and exam description. The query joins the 'student', 'programme', 'module', and 'exam' tables
     * to gather relevant information. The data is filtered based on the provided student ID.
     *
     * @param studentId The unique identifier of the student.
     * @return A list of {@code ModuleExamInfoDTO} objects containing information about module exams for the student.
     */
    @Select("SELECT m.module_id AS moduleId, m.module_name AS moduleName,e.exam_date AS examDate, e.exam_site AS examSite, e.exam_start_time AS examStartTime, e.exam_end_time AS examEndTime, e.exam_duration AS examDuration, e.exam_desc AS examDes " +
            "FROM student s " +
            "JOIN programme p ON s.programme_id = p.programme_id " +
            "JOIN module m ON p.programme_id = m.programme_id " +
            "JOIN exam e ON m.module_id = e.module_id " +
            "WHERE s.student_id = #{studentId} " )
    List<ModuleExamInfoDTO> getModuleExamInfo(String studentId);
}
