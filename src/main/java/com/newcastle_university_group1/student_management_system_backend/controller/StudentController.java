package com.newcastle_university_group1.student_management_system_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.newcastle_university_group1.student_management_system_backend.entity.Module;
import com.newcastle_university_group1.student_management_system_backend.entity.Programme;
import com.newcastle_university_group1.student_management_system_backend.entity.Student;
import com.newcastle_university_group1.student_management_system_backend.mapper.ModuleMapper;
import com.newcastle_university_group1.student_management_system_backend.mapper.ProgrammeMapper;
import com.newcastle_university_group1.student_management_system_backend.mapper.StudentMapper;
import com.newcastle_university_group1.student_management_system_backend.vo.RespBean;
import com.newcastle_university_group1.student_management_system_backend.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ronghui Zhong & Xuezhu Chen
 * @description:
 * @date 2024/4/5 2:58
 * @ProjectName student_management_system_backend
 **/
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    /**
     * Authorization: Student
     * @Author: Ronghui Zhong
     *
     * Retrieves a list of modules associated with a given student ID. This method first verifies the validity of the
     * provided student ID, then looks up the corresponding student record to determine the programme ID associated
     * with the student. Using the programme ID, it fetches and returns a list of modules that are part of the
     * student's programme.
     *
     * @param studentId The unique identifier of the student for whom modules are being requested. This ID is used
     *                  to locate the student's record and their associated programme.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain a list of {@code Module} objects associated with the student's programme. If the student ID is
     *         invalid, empty, or if no student matches the provided ID, an error {@code RespBean} is returned.
     */
    @GetMapping("/getModulesByStudentId")
    public RespBean getModulesByStudentId(String studentId){
        /**
         *  Verify the studentId from the front-end
         */
        if (studentId == "" && studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR);
        }
        /**
         *  Find Student
         */
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id", studentId);
        Student student = studentMapper.selectOne(studentQueryWrapper);
        /**
         *  If Not then return error
         */
        if (student == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        String id = student.getStudentId();
        String programmeId = student.getProgrammeId();
        /**
         *  Find modules by programme Id
         */
        QueryWrapper<Module> moduleQueryWrapper = new QueryWrapper<>();
        moduleQueryWrapper.eq("programme_id", programmeId);
        List<Module> modules = moduleMapper.selectList(moduleQueryWrapper);
        return RespBean.success(modules);
    }
}
