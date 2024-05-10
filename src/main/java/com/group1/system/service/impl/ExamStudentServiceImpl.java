package com.group1.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group1.system.entity.ExamStudent;
import com.group1.system.mapper.ExamStudentMapper;
import com.group1.system.service.IExamStudentService;
import org.springframework.stereotype.Service;
/**
 * @author Xuezhu Chen
 * @description: IExamStudent Service Implementation class
 *  This class encapsulates the logic related to module operations
 *  updating, deleting, and retrieving student user records.
 * @date 2024/4/19 20:05
 * @ProjectName Dyson Student Management System
 **/
@Service
public class ExamStudentServiceImpl extends ServiceImpl<ExamStudentMapper, ExamStudent> implements IExamStudentService {
}
