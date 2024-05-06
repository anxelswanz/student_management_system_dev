package com.newcastle_university_group1.student_management_system_backend.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newcastle_university_group1.student_management_system_backend.dto.SubmittedWorkDto;
import com.newcastle_university_group1.student_management_system_backend.dto.TimetableDTO;
import com.newcastle_university_group1.student_management_system_backend.dto.TutorStudentDTO;
import com.newcastle_university_group1.student_management_system_backend.entity.*;
import com.newcastle_university_group1.student_management_system_backend.mapper.*;
import com.newcastle_university_group1.student_management_system_backend.service.*;
import com.newcastle_university_group1.student_management_system_backend.vo.RespBean;
import com.newcastle_university_group1.student_management_system_backend.vo.RespBeanEnum;
import com.newcastle_university_group1.student_management_system_backend.vo.StaffStudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luo Jinwei / Jamie Cottrell / Ronghui Zhong
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
    private IModuleHistoryService iModuleHistoryService;

    @Resource
    private StaffMapper staffMapper;
    @Resource
    private StaffProgrammeLinkMapper staffProgrammeLinkMapper;
    @Resource
    private StudentTutorMapper studentTutorMapper;

    @Resource
    private ModuleHistoryMapper moduleHistoryMapper;
    @Resource
    private StudentMapper studentMapper;

    @Resource
    private TimetableMapper timetableMapper;


    /**
     * Authorization: Administrator
     * @Author: Jingwei Luo
     * Builds a timetable in the database using the given data of the {@link TimetableDTO} object,
     * and inerts the timetable into the database.
     * @param obj the {@link TimetableDTO} object has timetable information details,
     *            such as module time, nodule name, programme ID, class location , year and module ID.
     * @return A {@code RespBean} object indicating the result of the build operation and the insertion.
     */
    @PostMapping("/buildTimetable")
    public RespBean buildTimetable(@RequestBody TimetableDTO obj){

        return RespBean.success(timetableMapper.insert(Timetable.builder()
                .moduleTime(obj.getModuleTime())
                .moduleName(obj.getModuleName())
                .programmeId(obj.getProgrammeId())
                .classLocation(obj.getClassLocation())
                .year(obj.getYear())
                .moduleId(obj.getModuleId())
                .build()));
    }


    /**
     * Authorization: Administrator
     * @Author: Jingwei Luo
     * Updates a timetable in the database using the given data of the {@link TimetableDTO} object,
     * and inerts the updated timetable into the database.
     * @param obj the {@link TimetableDTO} object has timetable information details which should be updated,
     *            such as module time, nodule name, programme ID, class location , year and module ID.
     * @return A {@code RespBean} object indicating the result of the update operation.
     */
    @PostMapping("/updateTimetable")
    public RespBean updateTimetable(@RequestBody TimetableDTO obj){

        return RespBean.success(timetableMapper.updateById(Timetable.builder()
                .moduleTime(obj.getModuleTime())
                .moduleName(obj.getModuleName())
                .programmeId(obj.getProgrammeId())
                .classLocation(obj.getClassLocation())
                .year(obj.getYear())
                .moduleId(obj.getModuleId())
                .build()));
    }

    /**
     * Authorization: Staff (the administrator and tutors)
     * @Author: Jingwei Luo
     * Retrieves a paginated list of students with the information of their modules and module histories
     * (including coursework, exams, grades and academic history) based on filters and the type of the staff.
     * The administrator can see the information of all students.
     * Tutors can only see the information of their assigned students.
     * @param staffType The type of the staff who make the request, 0 represents the administrator, 1 represents tutors.
     * @param studentType The type of the students to filter by.
     * @param year The academic year of the students to filter by.
     * @param pageNum The page number for pagination, the default value is 1.
     * @param pageSize The number of records per page, the default value is 10.
     * @param tutorId The tutor ID which is the unique identifier of the tutor, required if the staff is a tutor.
     * @return A {@code RespBean} object contains a list of {@link StaffStudentVO}.
     */
    @GetMapping("/getAllStudents")
    public RespBean getAllStudents(@RequestParam Integer staffType,
                                   @RequestParam Integer studentType,
                                   @RequestParam Integer year,
                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                   @RequestParam(required = false) String tutorId){

        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<Student>();
        if(studentType != null){
            studentLambdaQueryWrapper.eq(Student::getStudentType,studentType);
        }
        if(year != null){
            studentLambdaQueryWrapper.eq(Student::getStudentYear,year);
        }

        List<StaffStudentVO> res = new ArrayList<>();
        Page<Student> studentList = new Page<>();
        if(staffType == 0){//if the staff is an administrator
            //1. get all students
            studentList = studentMapper.selectPage(new Page<>(pageNum, pageSize),studentLambdaQueryWrapper);
        }else{//if the staff is a tutor
            //1. get students who are assigned to this tutor
            if(StrUtil.isEmpty(tutorId)){
                return RespBean.success(res);
            }
            List<String> ids = staffMapper.getStudentIdByTutorId(tutorId);
            if(CollectionUtils.isNotEmpty(ids)){
                studentList = studentMapper.selectPage(new Page<>(pageNum, pageSize),
                        studentLambdaQueryWrapper.in(Student::getStudentId,ids));
            }else{
                return RespBean.success(res);
            }
        }

        if(studentList != null && CollectionUtils.isNotEmpty(studentList.getRecords())){
            for(Student student:studentList.getRecords()){
                StaffStudentVO staffStudentVO = StaffStudentVO.builder()
                        .studentId(student.getStudentId())
                        .studentYear(student.getStudentYear())
                        .build();

                //2. get modules
                //get the associated tutor ID based on the student ID,
                //and query the relevant module information using the tutor ID
                List<StudentTutor> studentTutors = studentTutorMapper.selectList(new LambdaQueryWrapper<StudentTutor>()
                        .eq(StudentTutor::getStudentId,student.getStudentId()));
                if(CollectionUtils.isNotEmpty(studentTutors)){
                    staffStudentVO.setModules(moduleMapper.selectList(new LambdaQueryWrapper<Module>()
                            .eq(Module::getStaffId,studentTutors.get(0).getTutorId())));
                }

                //3. get module history
                staffStudentVO.setModuleHistories(moduleHistoryMapper.selectList(new LambdaQueryWrapper<ModuleHistory>()
                        .eq(ModuleHistory::getStudentId,student.getStudentId())));

                res.add(staffStudentVO);
            }
        }

        return RespBean.success(res);
    }


    /**
     * Authorization: Staff (the administrator and tutors)
     * @Author: Jingwei Luo
     * Retrieves detailed information of a specific student, containing modules and module history information.
     * Searches for the student by the given student ID, and returns a list of modules and module history information
     * associated with this student.
     * @param studentId The student ID which is the unique identifier of the student
     * @return A {@code RespBean} containing a list of {@link StaffStudentVO}
     * representing the student's modules and module history.
     */
    @GetMapping("/getSpecificStudentModulesInfo")
    public RespBean getSpecificStudentModulesInfo (@RequestParam String studentId ){
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<Student>();
        if(studentId!=null){
            studentLambdaQueryWrapper.eq(Student::getStudentId,studentId);
        }
        List<StaffStudentVO> res = new ArrayList<>();
        List<Student> studentList = studentMapper.selectList(studentLambdaQueryWrapper);

        if(CollectionUtils.isNotEmpty(studentList)){
            for(Student student:studentList){
                StaffStudentVO staffStudentVO = StaffStudentVO.builder().studentId(student.getStudentId())
                        .studentYear(student.getStudentYear())
                        .build();
                //get modules
                //get the associated tutor ID based on the student ID,
                //and query the relevant module information using the tutor ID
                List<StudentTutor> studentTutors = studentTutorMapper.selectList(new LambdaQueryWrapper<StudentTutor>()
                        .eq(StudentTutor::getStudentId,student.getStudentId()));
                if(CollectionUtils.isNotEmpty(studentTutors)){
                    staffStudentVO.setModules(moduleMapper.selectList(new LambdaQueryWrapper<Module>()
                            .eq(Module::getStaffId,studentTutors.get(0).getTutorId())));
                }

                //get module history
                staffStudentVO.setModuleHistories(moduleHistoryMapper.selectList(new LambdaQueryWrapper<ModuleHistory>()
                        .eq(ModuleHistory::getStudentId,student.getStudentId())));

                res.add(staffStudentVO);
            }
        }

        return RespBean.success(res);
    }


    /**
     * Authorization: Administrator
     * @Author: Jingwei Luo
     * Retrieves a paginated list of all staff.
     * @param pageNum The page number for pagination, the default value is 1.
     * @param pageSize The number of records per page, the default value is 10.
     * @return a {@link RespBean} object containing a paginated list of staff.
     * Returns an empty list if no staff records are found.
     */
    @GetMapping
    public RespBean getAllStaff(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize){

        return RespBean.success(staffMapper.selectPage(new Page<>(pageNum, pageSize),null));
    }

    /**
     * Authorization: Administrator
     * @Author: Jingwei Luo
     * Retrieves a paginated list of a specific staff with the given staff ID.
     * @param staffId The staff ID which is the unique identifier of the staff.
     * @return a {@link RespBean} object containing the detailed information of this staff.
     * Returns an empty result if no staff is found.
     */
    @GetMapping("/getStaffById")
    public RespBean getStaffById(@RequestParam String staffId) {
        return RespBean.success(staffMapper.selectOne(new LambdaQueryWrapper<Staff>().eq(Staff::getStaffId,staffId)));
    }

    /**
     * Authorization: Administrator
     * @Author: Jingwei Luo
     * Updates the information of an existing staff in the database.
     * Takes a {@link Staff} object as input, containing the new information of the staff,
     * and the staff ID of the object can be used to find the record to be updated.
     * @param staff The {@link Staff} object containing the new information of the staff to be updated.
     *              The object must have the staff ID to find the record to be updated.
     * @return a {@link RespBean} object indicating the result of the operation.
     */
    @PostMapping("/update")
    public RespBean update(@RequestBody Staff staff) {
        staffMapper.updateById(staff);
        return RespBean.success();
    }

    /**
     * Authorization: Administrator
     * @Author: Jingwei Luo
     * Deletes the staff with the given staff ID from the database.
     * Uses the given staff ID to find the staff to be deleted.
     * @param staffId The staff ID which is the unique identifier of the staff.
     * @return a {@link RespBean} object indicating the result of the operation.
     */
    @GetMapping("/delete")
    public RespBean delete(@RequestParam String staffId) {
        staffMapper.delete(new LambdaQueryWrapper<Staff>().eq(Staff::getStaffId,staffId));
        return RespBean.success();
    }


    /**
     * Authorization: Administrator
     * @Author: Jingwei Luo
     * Retrieves a list of programmes associated with a specific staff, identified by the given staff ID.
     * @param staffId The staff ID which is the unique identifier of the staff.
     * @return a {@link RespBean} object containing a list of {@link StaffProgrammeLink} objects.
     * Returns an empty list if no staff programme links are found.
     * Each {@link StaffProgrammeLink} object links the staff and the programme.
     */
    @GetMapping("/staffProgrammeLink")
    public RespBean staffProgrammeLink(@RequestParam String staffId) {
        return RespBean.success(staffProgrammeLinkMapper
                .selectList(new LambdaQueryWrapper<StaffProgrammeLink>().eq(StaffProgrammeLink::getStaffId,staffId)));
    }



    /**
     * Authorization: Administrator
     * @Author: Jingwei Luo
     * Updates an existing staff-programme link in the database based on the given staff-programme link object.
     * The given {@link StaffProgrammeLink} object contains the staff ID as an identifier of the link
     * and new values to be updated.
     * @param staffProgrammeLinkDTO The {@link StaffProgrammeLink} object contains the updated data for the link,
     *                              and must include the staff ID as an identifier
     *                              to find the existing staff record in the database
     * @return a {@link RespBean} object indicating the result of the operation.
     */
    @PostMapping("/staffProgrammeLink")
    public RespBean updateStaffProgrammeLink(@RequestBody StaffProgrammeLink staffProgrammeLinkDTO) {
        staffProgrammeLinkMapper.updateById(staffProgrammeLinkDTO);
        return RespBean.success();
    }

    /**
     * Authorization: Administrator
     * @Author: Jingwei Luo
     * Deletes all staff-programme links associated with the specified staff from the database
     * based on the given staff ID.
     * @param staffId The staff ID which is the unique identifier of the staff.
     * @return a {@link RespBean} object indicating the result of the operation.
     */
    @GetMapping("/staffProgrammeLink/deleteStaffProgrammeLink")
    public RespBean deleteStaffProgrammeLink(@RequestParam String staffId) {
        staffProgrammeLinkMapper.delete(new LambdaQueryWrapper<StaffProgrammeLink>().eq(StaffProgrammeLink::getStaffId,staffId));
        return RespBean.success();
    }



    /**
     * @Author: Ronghui Zhong
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
     * @Author: Ronghui Zhong
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
     * @Author: Ronghui Zhong
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
     * @Author: Ronghui Zhong
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
     * @Author: Ronghui Zhong
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
     * @Author: Ronghui Zhong
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


    private IStudentTutorService studentTutorService;

    /**
     * @Author: Jamie Cottrell
     * Retrieves a list of all staff Ids from the Staff entity.
     * @return a {@link RespBean} object containing the list of staff Ids..
     */
    @GetMapping("/getAllTeachers")
    public RespBean getAllTeachers() {
        List<String> allTeachers = staffMapper.getAllTeachers();
        return RespBean.success(allTeachers);
    }

    /**
     * @Author: Jamie Cottrell
     * Retrieves the available Student Ids.
     * Assigns the total number of students from the student_tutor table who have student Ids beginning with 'S',
     * followed by the current year to integer variable 'totalNumber1'.
     * Assigns the total number of students from the student table who have student Ids beginning with 'S',
     * followed by the current year to integer variable 'totalNumber2'.
     * @return a {@link RespBean} object containing the tutorStudentDTO, which has its fields
     * firstStudent and lastStudent set to the calculated String variables.
     */
    @GetMapping("/getAvailableStudentIds")
    public RespBean getAvailableStudentIds() {
        //student tutor
        int totalNumber1 = staffMapper.total_number1();
        //student
        int totalNumber2 = staffMapper.total_number2(); //student
        String firstStudent = "S24" + (totalNumber1 + 1);
        String lastStudent = null;

        if (totalNumber2 - totalNumber1 >= 15) {
            lastStudent = String.valueOf(totalNumber1);
        } else if (totalNumber2 - totalNumber1 < 15) {
            lastStudent = String.valueOf(totalNumber2);
        }

        TutorStudentDTO tutorStudentDTO = new TutorStudentDTO();
        tutorStudentDTO.setFirstStudentId(firstStudent);
        tutorStudentDTO.setLastStudentId(lastStudent);

        return RespBean.success(tutorStudentDTO);
    }

    /**
     * @Author: Jamie Cottrell
     * Saves Tutor and Student information using the saveTutorAndStudents method in the studentTutor
     * Service class.
     * the staffId, firstStudent and lastStudent variables are set based on the information stored in
     * the tutorStudentDTO
     * @return a {@link RespBean} success response to confirm the save was successful.
     */

    @PostMapping("/saveTutorAndStudent")
    public RespBean saveTutorAndStudent(@RequestBody TutorStudentDTO tutorStudentDTO) {
        String staffId = tutorStudentDTO.getStaffId();
        String firstStudent = tutorStudentDTO.getFirstStudentId();
        String lastStudent = tutorStudentDTO.getLastStudentId();
        studentTutorService.saveTutorAndStudents(firstStudent, lastStudent, staffId);
        return RespBean.success();
    }

    /**
     * @Author: Jamie Cottrell
     * a list of Students with their assigned tutors is assigned to the tutorWithStudents List of Strings.
     * This list is converted into a String array.
     * @return a {@link RespBean} object containing the tutorsWithStudentsArray.
     */
    @GetMapping("/showTutorHistory")
    public RespBean showTutorHistory() {
        List<String> tutorsWithStudents = staffMapper.getAllTutorsWithStudents();
        String[] tutorsWithStudentsArray = tutorsWithStudents.toArray(new String[0]);
        return RespBean.success(tutorsWithStudentsArray);
    }

}

