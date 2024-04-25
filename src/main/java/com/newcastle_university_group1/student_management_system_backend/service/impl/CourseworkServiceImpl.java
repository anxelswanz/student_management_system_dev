package com.newcastle_university_group1.student_management_system_backend.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newcastle_university_group1.student_management_system_backend.entity.Coursework;
import com.newcastle_university_group1.student_management_system_backend.mapper.CourseworkMapper;
import com.newcastle_university_group1.student_management_system_backend.service.ICourseworkService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Ronghui Zhong
 * @description: ICourseworkService Implementation class
 *  This class encapsulates the logic related to module operations
 * @date 2024/4/7 21:00
 * @ProjectName Dyson Student Management System
 **/
@Service
public class CourseworkServiceImpl extends ServiceImpl<CourseworkMapper, Coursework> implements ICourseworkService {
    public String getUploadCoursework(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "Please select a file to upload";
        }
        // Setting the file storage path
        String fileName = file.getOriginalFilename();
        // Select the address to be stored
        String filePath = "D:/HuaweiMoveData/Users/67659/Desktop/";
        try {
            // Creating file paths
            File dest = new File(filePath + fileName);
            // Storage Files
            file.transferTo(dest);
            return "File uploaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file";
        }
    }
}
