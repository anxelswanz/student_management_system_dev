package com.newcastle_university_group1.student_management_system_backend.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newcastle_university_group1.student_management_system_backend.dto.StudentStudyRecordDTO;
import com.newcastle_university_group1.student_management_system_backend.entity.Module;
import com.newcastle_university_group1.student_management_system_backend.mapper.ModuleMapper;
import com.newcastle_university_group1.student_management_system_backend.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ronghui Zhong
 * @description: IModuleService Implementation class
 *  This class encapsulates the logic related to module operations
 * @date 2024/4/4 18:00
 * @ProjectName Dyson Student Management System
 **/
@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements IModuleService {
    @Autowired
    private ModuleMapper moduleMapper;

    @Override
    public List<Module> getModuleTime(String studentId) {
        List<Module> modules = moduleMapper.getModuleTime(studentId);
        for (Module module : modules) {
            String startTime = module.getDate().substring(0, 2);
            int start = Integer.parseInt(startTime);
            // Depending on the type of module determines the value to be added
            int num = module.getType() != null && module.getType().equals("Mechanical") ? 1 : 2;
            // 12 months in a year, dealing with situations beyond "12"
            start = ((start + num) % 12 == 0) ? 12 : (start + num) % 12;
            String incrementedString = String.format("%02d", start);
            String remainingString = module.getDate().substring(3);
            // Splicing new strings
            String endTime = incrementedString + "-" + remainingString;

        }
        return modules;
    }

    @Override
    public IPage<StudentStudyRecordDTO> getStudyRecordWithPagination(String studentId, Page<StudentStudyRecordDTO> page) {
        return moduleMapper.getStudyRecordWithPagination(studentId,page);

    }

}
