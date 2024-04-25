package com.newcastle_university_group1.student_management_system_backend.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newcastle_university_group1.student_management_system_backend.dto.ModuleExamInfoDTO;
import com.newcastle_university_group1.student_management_system_backend.entity.Exam;
import com.newcastle_university_group1.student_management_system_backend.mapper.ExamMapper;
import com.newcastle_university_group1.student_management_system_backend.service.IExamService;
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
    @Override
    public List<ModuleExamInfoDTO> getModuleExamInfo(String studentId) {
        return examMapper.getModuleExamInfo(studentId);
    }
}
