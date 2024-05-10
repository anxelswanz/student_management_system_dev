package com.group1.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group1.system.entity.StudentTutor;
import com.group1.system.mapper.StudentTutorMapper;
import com.group1.system.service.IStudentTutorService;
import com.group1.system.vo.RespBean;
import org.springframework.stereotype.Service;


/**
 * @author Ronghui Zhong
 * @description: IStudentTutor Service Implementation class
 * @date 2024/4/26 21:30
 * @ProjectName Dyson Student Management System
 **/
@Service
public class StudentTutorServiceImpl extends ServiceImpl<StudentTutorMapper, StudentTutor> implements IStudentTutorService {

    private IStudentTutorService iStudentTutorService;

    /**
     * @Author: Jamie Cottrell
     * Method that receives information from TutorStudentDTO as parameters. The for loop iterates through the
     * students in the list, combining it to a staff Id and saving it as a studentTutor object (staffId, studentId)
     * @return a {@link RespBean} containing a success message.
     */
    public RespBean saveTutorAndStudents(String firstStudent, String lastStudent, String staffId) {

        for (String studentId = firstStudent; studentId.compareTo(lastStudent) <= 0; studentId = getNextStudentId(studentId)) {
            StudentTutor studentTutor = new StudentTutor(staffId, studentId);
            iStudentTutorService.save(studentTutor);
        }
        return RespBean.success();
    }

    /**
     * @Author: Jamie Cottrell
     * Method to get the next student Id in the list. String sYy will be the first 3 characters of the student Id,
     * (e.g. S24). int id is the extracted substring starting from index 3. It is the numeric part of the student Id.
     * It is then converted into an integer and 1 is added, increasing the student ID number by 1.
     * sYy is concatenated with the numeric part which is formatted using %04d, insuring the number is padded with zeros if
     * it's not long enough, and that the id variable is a 4-digit number.
     */
    public String getNextStudentId (String studentId) {
        //sYy =  S + year (e.g S24)
        String sYy = studentId.substring(0, 3); // Assuming "S24"
        int id = Integer.parseInt(studentId.substring(3)) + 1;
        return sYy + String.format("%04d", id);
    }
}
