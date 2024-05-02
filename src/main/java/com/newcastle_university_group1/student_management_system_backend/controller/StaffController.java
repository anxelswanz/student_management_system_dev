package com.newcastle_university_group1.student_management_system_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.newcastle_university_group1.student_management_system_backend.dto.SubmittedWorkDto;
import com.newcastle_university_group1.student_management_system_backend.entity.*;
import com.newcastle_university_group1.student_management_system_backend.mapper.*;
import com.newcastle_university_group1.student_management_system_backend.service.IAbsenceService;
import com.newcastle_university_group1.student_management_system_backend.service.ICourseworkService;
import com.newcastle_university_group1.student_management_system_backend.service.IModuleHistoryService;
import com.newcastle_university_group1.student_management_system_backend.service.IStaffService;
import com.newcastle_university_group1.student_management_system_backend.vo.RespBean;
import com.newcastle_university_group1.student_management_system_backend.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ansel Zhong
 * @description:
 * @date 2024/4/30 16:24
 * @ProjectName student_management_system_backend
 **/
@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private AbsenceMapper absenceMapper;

    @Autowired
    private IAbsenceService iAbsenceService;

    @Autowired
    private IStaffService staffService;

    @Autowired
    private CourseworkMapper courseworkMapper;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private CourseworkStudentMapper courseworkStudentMapper;

    @Autowired
    private ExamStudentMapper examStudentMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ICourseworkService courseworkService;

    @Autowired
    private IModuleHistoryService iModuleHistoryService;

    /**
     * Retrieves all absence requests based on staff ID.
     *
     * @param staffId the ID of the staff
     * @return a response bean containing a list of absence requests
     * @author Ronghui Zhong
     * @date 2024/4/30
     */
    @GetMapping("/getAllAbsenceRequests")
    public RespBean getAllAbsenceRequests(String staffId){
        if (staffId == null || staffId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR);
        }
        QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
        staffQueryWrapper.eq("staff_id",staffId);
        Staff one = staffService.getOne(staffQueryWrapper);
        if (one.getStaffType() == 0) {
            QueryWrapper<Absence> absenceQueryWrapper = new QueryWrapper<>();
            List<Absence> list = iAbsenceService.list();
            absenceQueryWrapper.eq("status", 0);
            return RespBean.success(list);
        } else {
            QueryWrapper<Absence> absenceQueryWrapper = new QueryWrapper<>();
            absenceQueryWrapper.eq("staff_id",staffId);
            absenceQueryWrapper.eq("status", 0);
            List<Absence> list = iAbsenceService.list(absenceQueryWrapper);
            return RespBean.success(list);
        }
    }

    /**
     * Updates the status of an absence request.
     *
     * @param absenceId the ID of the absence request
     * @param status the new status of the absence request
     * @return a response bean indicating the success or failure of the operation
     * @author Ronghui Zhong
     * @date 2024/4/30
     */
    @GetMapping("/updateAbsenceRequest")
    public RespBean updateAbsenceRequest(String absenceId, Integer status) {
        if (absenceId == null || absenceId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR);
        }
         QueryWrapper<Absence> absenceQueryWrapper = new QueryWrapper<>();
         absenceQueryWrapper.eq("absence_id",absenceId);
         Absence one = iAbsenceService.getOne(absenceQueryWrapper);
         one.setStatus(status);
         absenceMapper.updateById(one);
         return RespBean.success();
    }


    /**
     * Posts a coursework to the system.
     *
     * @param coursework the coursework to be posted
     * @return a response bean indicating the success or failure of the operation
     * @author Ronghui Zhong
     * @date 2024/4/30
     */
    @PostMapping("/postCoursework")
    public RespBean postCoursework(@RequestBody Coursework coursework) {
        if (coursework == null) {
            return RespBean.error(RespBeanEnum.ERROR);
        }
        courseworkMapper.insert(coursework);
        return RespBean.success();
    }

    /**
     * Posts an exam to the system.
     *
     * @param exam the exam to be posted
     * @return a response bean indicating the success or failure of the operation
     * @author Ronghui Zhong
     * @date 2024/4/30
     */
    @PostMapping("/postExam")
    public RespBean postExam(@RequestBody Exam exam) {
        if (exam == null)
            return RespBean.error(RespBeanEnum.ERROR);
        examMapper.insert(exam);
        return RespBean.success();
    }


    /**
     * Retrieves all work submitted by students for a staff member.
     *
     * @param staffId the ID of the staff member
     * @return a response bean containing a list of submitted work DTOs
     * @author Ronghui Zhong
     * @date 2024/4/30
     */
    @GetMapping("/getAllWork")
    public RespBean getAllWork(String staffId){
        if (staffId == null) {
            return RespBean.error(RespBeanEnum.ERROR);
        }
        QueryWrapper<Module> moduleQueryWrapper = new QueryWrapper<>();
        moduleQueryWrapper.eq("staff_id", staffId);
        List<Module> modules = moduleMapper.selectList(moduleQueryWrapper);

        List<SubmittedWorkDto> list = new ArrayList<>();
        for (Module module : modules) {
            QueryWrapper<CourseworkStudent> courseworkStudentQueryWrapper = new QueryWrapper<>();
            courseworkStudentQueryWrapper.eq("module_id", module.getModuleId());
            List<CourseworkStudent> courseworkStudents = courseworkStudentMapper.selectList(courseworkStudentQueryWrapper);
            for (CourseworkStudent courseworkStudent : courseworkStudents) {
                if (courseworkStudent.getMark() == -1) {
                    SubmittedWorkDto submittedWorkDto = new SubmittedWorkDto();
                    submittedWorkDto.setStudentId(courseworkStudent.getStudentId());
                    submittedWorkDto.setModuleId(module.getModuleId());
                    submittedWorkDto.setType("Coursework");
                    submittedWorkDto.setSubmittedStatus("Submitted");
                    submittedWorkDto.setId(courseworkStudent.getCourseworkId());
                    list.add(submittedWorkDto);
                }
            }
        }

        for (Module module : modules) {
            String moduleId = module.getModuleId();
            QueryWrapper<ExamStudent> examStudentQueryWrapper = new QueryWrapper<>();
            examStudentQueryWrapper.eq("module_id", moduleId);
            List<ExamStudent> examStudents = examStudentMapper.selectList(examStudentQueryWrapper);
            for (ExamStudent examStudent : examStudents) {
                if (examStudent.getMark() == -1) {
                    SubmittedWorkDto submittedWorkDto = new SubmittedWorkDto();
                    submittedWorkDto.setModuleId(moduleId);
                    submittedWorkDto.setType("Exam");
                    submittedWorkDto.setStudentId(examStudent.getStudentId());
                    submittedWorkDto.setSubmittedStatus("Submitted");
                    submittedWorkDto.setId(examStudent.getExamId());
                    list.add(submittedWorkDto);
                }
            }
        }
        return RespBean.success(list);
    }


    /**
     * Updates the mark of a piece of work.
     *
     * @param id the ID of the work
     * @param type the type of the work (Coursework or Exam)
     * @param mark the new mark for the work
     * @return a response bean indicating the success or failure of the operation
     * @author Ronghui Zhong
     * @date 2024/4/30
     */
    @GetMapping("/updateWork")
    public RespBean updateWork(String id, String type, double mark){
        if (type == null || id == null ) {
            return RespBean.error(RespBeanEnum.ERROR);
        }

        if (type.equals("Coursework")) {
            QueryWrapper<CourseworkStudent> courseworkStudentQueryWrapper = new QueryWrapper<>();
            courseworkStudentQueryWrapper.eq("coursework_id",id);
            CourseworkStudent courseworkStudent = courseworkStudentMapper.selectOne(courseworkStudentQueryWrapper);
            if (courseworkStudent == null) {
              return RespBean.error(RespBeanEnum.ERROR, "Wrong Coursework Id");
            }
            courseworkStudent.setMark(mark);
            int i = courseworkStudentMapper.updateById(courseworkStudent);
            return RespBean.success();
        } else if (type.equals("Exam")) {
            QueryWrapper<ExamStudent> examStudentQueryWrapper = new QueryWrapper<>();
            examStudentQueryWrapper.eq("exam_id",id);
            ExamStudent examStudent = examStudentMapper.selectOne(examStudentQueryWrapper);
            if (examStudent == null) {
                return RespBean.error(RespBeanEnum.ERROR, "Wrong Exam Id");
            }
            examStudent.setMark(mark);
            examStudentMapper.updateById(examStudent);
            return RespBean.success();
        } else {
            return RespBean.error(RespBeanEnum.ERROR);
        }
    }

    /**
     * Calculates the final mark for a student in a module.
     *
     * @param moduleId the ID of the module
     * @param studentId the ID of the student
     * @return a response bean containing the calculated final mark and module history information
     * @author Ronghui Zhong
     * @date 2024/4/30
     */
    @GetMapping("/calculateFinalMark")
    public RespBean calculateFinalMark(String moduleId, String studentId){
        /**
         *  The final mark is composed of 40% of Coursework Mark and 60% of Exam Mark
         */
        QueryWrapper<CourseworkStudent> courseworkStudentQueryWrapper = new QueryWrapper<>();
        courseworkStudentQueryWrapper.eq("student_id",studentId);
        courseworkStudentQueryWrapper.eq("module_id", moduleId);
        CourseworkStudent courseworkStudent
                = courseworkStudentMapper.selectOne(courseworkStudentQueryWrapper);
        double courseworkStudentMark = 0;
        if (courseworkStudent != null) {
            courseworkStudentMark = courseworkStudent.getMark();
        }

        QueryWrapper<ExamStudent> examStudentQueryWrapper = new QueryWrapper<>();
        examStudentQueryWrapper.eq("student_id",studentId);
        examStudentQueryWrapper.eq("module_id", moduleId);
        ExamStudent examStudent = examStudentMapper.selectOne(examStudentQueryWrapper);
        double examStudentMark = 0;
        if (examStudent != null) {
            examStudentMark =  examStudent.getMark();
        }

        final double finalMark = 0.4 * courseworkStudentMark + 0.6 * examStudentMark;

        /**
         *  find module name
         */
        QueryWrapper<Module> moduleQueryWrapper = new QueryWrapper<>();
        moduleQueryWrapper.eq("module_id", moduleId);
        Module module = moduleMapper.selectOne(moduleQueryWrapper);
        if (module == null) {
            return RespBean.error(RespBeanEnum.ERROR);
        }
        ModuleHistory moduleHistory = new ModuleHistory();
        moduleHistory.setModuleName(module.getModuleName());
        moduleHistory.setStudentId(studentId);
        moduleHistory.setCourseworkMark(courseworkStudentMark);
        moduleHistory.setExamMark(examStudentMark);
        moduleHistory.setTotalMark(finalMark);
        moduleHistory.setCredits(module.getCredits());
        moduleHistory.setModuleId(moduleId);
        if (finalMark >= 60) {
            moduleHistory.setResult(1); // pass
        } else {
            moduleHistory.setResult(0); // fail
        }
        QueryWrapper<ModuleHistory> moduleHistoryQueryWrapper = new QueryWrapper<>();
        moduleHistoryQueryWrapper.eq("module_id", moduleId);
        moduleHistoryQueryWrapper.eq("student_id", studentId);
        ModuleHistory one = iModuleHistoryService.getOne(moduleHistoryQueryWrapper);
        if (one != null) {
            iModuleHistoryService.updateById(moduleHistory);
        } else {
            iModuleHistoryService.save(moduleHistory);
        }
        return RespBean.success(moduleHistory);
    }
}

