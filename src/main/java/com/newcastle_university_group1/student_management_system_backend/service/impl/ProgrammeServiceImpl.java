package com.newcastle_university_group1.student_management_system_backend.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newcastle_university_group1.student_management_system_backend.entity.Programme;
import com.newcastle_university_group1.student_management_system_backend.mapper.ProgrammeMapper;
import com.newcastle_university_group1.student_management_system_backend.service.IProgrammeService;
import org.springframework.stereotype.Service;

/**
 * @author Ronghui Zhong
 * @description: IProgrammeService Implementation class
 *  This class encapsulates the logic related to module operations
 * @date 2024/4/4 22:10
 * @ProjectName Dyson Student Management System
 **/
@Service
public class ProgrammeServiceImpl extends ServiceImpl<ProgrammeMapper, Programme> implements IProgrammeService {

}
