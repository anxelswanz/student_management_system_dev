package com.newcastle_university_group1.student_management_system_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newcastle_university_group1.student_management_system_backend.entity.StudentTutor;
import com.newcastle_university_group1.student_management_system_backend.vo.RespBean;

public interface IStudentTutorService extends IService<StudentTutor> {
    public RespBean saveTutorAndStudents(String firstStudent, String lastStudent, String staffId);

    public String getNextStudentId (String studentId);
}
