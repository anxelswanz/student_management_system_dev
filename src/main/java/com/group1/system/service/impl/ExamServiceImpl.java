package com.group1.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group1.system.dto.ModuleExamInfoDTO;
import com.group1.system.entity.Exam;
import com.group1.system.mapper.ExamMapper;
import com.group1.system.service.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ronghui Zhong
 * @description: IExamService Implementation class
 *  This class encapsulates the logic related to module operations
 * @date 2024/4/7 20:00
 * @ProjectName Dyson Student Management System
 **/
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements IExamService {
    @Autowired
    private ExamMapper examMapper;

    /**
     * Authorization: Student
     * @author Xuezhu Chen
     *
     * This method retrieves information about module exams for a student from the database using the provided student ID.
     *
     * @param studentId The unique identifier of the student.
     * @return A list of {@code ModuleExamInfoDTO} objects containing information about module exams for the student.
     */
    @Override
    public List<ModuleExamInfoDTO> getModuleExamInfo(String studentId) {
        return examMapper.getModuleExamInfo(studentId);
    }
}
