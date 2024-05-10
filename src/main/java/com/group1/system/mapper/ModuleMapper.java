package com.group1.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group1.system.dto.StudentStudyRecordDTO;
import com.group1.system.entity.Module;
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

    /**
     * Authorization: Student
     * @author Xuezhu Chen
     *
     * This method retrieves the study record for a student with pagination from the database. It executes a SQL query
     * to fetch data including module ID, module name, coursework mark, exam mark, and total mark. The query joins the
     * 'student', 'programme', 'module', 'coursework_student', and 'exam_student' tables to gather relevant information.
     * The data is filtered based on the provided student ID and is paginated using the provided page information.
     *
     * @param studentId The unique identifier of the student.
     * @param page The pagination information including current page and page size.
     * @return A {@code IPage<StudentStudyRecordDTO>} object containing the study record for the student with pagination.
     */
    @Select("SELECT m2.module_name AS moduleName, cs.module_id AS moduleId, cs.mark AS courseworkMark, es.mark AS examMark, (cs.mark * 0.4 + es.mark * 0.6) AS totalMark " +
            "FROM student s " +
            "JOIN programme p ON s.programme_id = p.programme_id " +
            "JOIN module m1 ON p.programme_id = m1.programme_id " + // 添加了连接条件
            "JOIN coursework_student cs ON s.student_id = cs.student_id " +
            "JOIN exam_student es ON s.student_id = es.student_id " +
            "JOIN module m2 ON cs.module_id = m2.module_id " + // 使用不同的别名
            "WHERE s.student_id = #{studentId} " +
            "LIMIT #{page.current}, #{page.size}")
    IPage<StudentStudyRecordDTO> getStudyRecordWithPagination(String studentId, Page<StudentStudyRecordDTO> page);

    /**
     * @Author: Jingwei Luo
     * Retrieves a list of student IDs that are currently included in any timetable.
     * It is useful for identifying modules that have scheduled times
     * @return A list of module IDs that are present in the timetable.
     */
    @Select("SELECT module_id FROM MODULE WHERE module_id IN " +
            " ( SELECT  module_id FROM timetable ) ")
    List<String> getAllTimeTableModuleId();

    /**
     * @Author: Jingwei Luo
     * Retrieves a list of module IDs that are not currently included in any timetable.
     * It can be useful for identifying modules that are currently not be scheduled
     * @return A list of module IDs that are present in the timetable.
     */
    @Select("SELECT module_id FROM MODULE WHERE module_id NOT IN " +
            " ( SELECT  module_id FROM timetable ) ")
    List<String> getNotInTimeTableModuleId();
}
