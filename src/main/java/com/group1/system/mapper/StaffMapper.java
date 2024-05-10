package com.group1.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.group1.system.entity.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author Ronghui Zhong
 * @description: StaffMapper Interface for CRUD
 * @date 2024/3/29 21:55
 * @Modified By: Jingwei Luo / Jamie Cottrel
 * @ProjectName Dyson Student Management System
 **/
@Mapper
public interface StaffMapper extends BaseMapper<Staff> {

    /**
     * @Author: Jingwei Luo
     * Retrieves a list of student IDs for students who are assigned to the tutor with the given tutor ID.
     * @param tutorId the ID (unique identifier) of the tutor
     * @return a list of student IDs for students who are assigned to the specific tutor
     */
    @Select("Select student_id from student_tutor where tutor_id = #{tutorId}")
    List<String> getStudentIdByTutorId(String tutorId);

    /**
     * @Author: Jamie Cottrell
     * SQL query to select all the staff Ids from the staff table.
     */
    @Select("SELECT staff_id FROM staff")
    List<String> getAllTeachers();

    /**
     * @Author: Jamie Cottrell
     * SQL query to find the total number of students from student_Tutor table who have an S, followed
     * by the current year (e.g 24)
     */
    @Select("SELECT COUNT(*) AS total_student FROM student_tutor WHERE student_id LIKE CONCAT('S', RIGHT(YEAR(CURRENT_DATE()), 2), '%')")
    int total_number1();

    /**
     * @Author: Jamie Cottrell
     * SQL query to find the total number of students from student table who have an S, followed
     * by the current year (e.g 24)
     */
    @Select("SELECT COUNT(*) AS total_student FROM student WHERE student_id LIKE CONCAT('S', RIGHT(YEAR(CURRENT_DATE()), 2), '%')")
    int total_number2();

    /**
     * @Author: Jamie Cottrell
     * SQL query that retrieves the first 15 combinations of student and tutor Ids, ordered by student ID
     * within each tutor group. Row_Number() assigns a sequential number to each row within each
     * tutor group, ordered by student Id. The outer query filters the results so that only
     * rows that are less than or equal to 15 students are included.
     */
    @Select("SELECT tutor_id, student_id FROM (SELECT tutor_id, student_id, ROW_NUMBER() OVER (PARTITION BY tutor_id ORDER BY student_id) AS row_num FROM student_tutor) AS numbered WHERE row_num <= 15")
    List<String> getAllTutorsWithStudents();
}
