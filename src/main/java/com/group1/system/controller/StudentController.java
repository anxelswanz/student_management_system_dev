package com.group1.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group1.system.dto.ModuleExamInfoDTO;
import com.group1.system.dto.StudentStudyRecordDTO;
import com.group1.system.entity.*;
import com.group1.system.mapper.ModuleHistoryMapper;
import com.group1.system.mapper.ModuleMapper;
import com.group1.system.mapper.StudentMapper;
import com.group1.system.service.*;
import com.group1.system.vo.RespBean;
import com.group1.system.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Ronghui Zhong & Xuezhu Chen
 * @description:
 *
 * Module Information Retrieval: Students can fetch information related to the modules they are enrolled in,
 * such as module IDs, names, credits, and detailed descriptions.
 * Programme Details Access: The controller allows students to retrieve detailed information about their academic programmes,
 * including programme names and descriptions.
 * Academic Record Management: It supports operations to view and manage academic histories,
 * such as viewing module exams and coursework details, submitting coursework, and checking grades.
 * Exam Management: Students can get information about their exams, submit exams, and view their exam marks.
 * Absence Management: This functionality allows students to submit absence requests and view their absence records.
 * Personal and Academic Support: The controller provides access to personal tutor information.
 * Email and Contact Management: Students can get  email addresses of staff members related to their modules.
 * @date 2024/4/5 2:58
 * @ProjectName student_management_system_backend
 **/
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IProgrammeService iProgrammeService;

    @Autowired
    private IModuleService iModuleService;

    @Autowired
    private ICourseworkService iCourseworkService;

    @Autowired
    private ICourseworkStudentService iCourseworkStudentService;

    @Autowired
    private IExamService iExamService;

    @Autowired
    private IStaffService iStaffService;

    @Autowired
    private IExamStudentService iExamStudentService;

    @Autowired
    private IStudentTutorService iStudentTutorService;


    @Autowired
    private IAbsenceService iAbsenceService;

    @Autowired
    private ModuleHistoryMapper moduleHistoryMapper;
    /**
     * Authorization: Student
     * @Author: Ronghui Zhong
     *
     * Retrieves a list of modules associated with a given student ID. This method first verifies the validity of the
     * provided student ID, then looks up the corresponding student record to determine the programme ID associated
     * with the student. Using the programme ID, it fetches and returns a list of modules that are part of the
     * student's programme.
     *
     * @param studentId The unique identifier of the student for whom modules are being requested. This ID is used
     *                  to locate the student's record and their associated programme.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain a list of {@code Module} objects associated with the student's programme. If the student ID is
     *         invalid, empty, or if no student matches the provided ID, an error {@code RespBean} is returned.
     */
    @GetMapping("/getModulesByStudentId")
    public RespBean getModulesByStudentId(String studentId){
        /**
         *  Verify the studentId from the front-end
         */
        if (studentId == null && studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR);
        }
        /**
         *  Find Student
         */
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id", studentId);
        Student student = studentMapper.selectOne(studentQueryWrapper);
        /**
         *  If Not then return error
         */
        if (student == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        String id = student.getStudentId();
        String programmeId = student.getProgrammeId();
        /**
         *  Find modules by programme Id
         */
        QueryWrapper<Module> moduleQueryWrapper = new QueryWrapper<>();
        moduleQueryWrapper.eq("programme_id", programmeId);
        List<Module> modules = moduleMapper.selectList(moduleQueryWrapper);
        return RespBean.success(modules);
    }


    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the name of the programme associated with the given student ID.
     * This method verifies the validity of the provided student ID. If the student ID is empty or null, an error
     * response is returned indicating the inability to find a student with the provided ID. It then queries the
     * corresponding student record using the provided student ID. If no student is found with the provided ID, an error
     * response is returned. Once the student is found, it retrieves the programme ID associated with the student. Using
     * this programme ID, it queries the corresponding programme record. If no programme is found with the retrieved
     * programme ID, an error response is returned.
     *
     * @param studentId The unique identifier of the student for whom the programme name is being requested.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain the name of the programme associated with the student ID. If the student ID is invalid, empty,
     *         or if no student matches the provided ID, an error {@code RespBean} is returned.
     */

    @GetMapping("/programmeName")
    public RespBean programmeName(@RequestParam String studentId) {
        // Verify the studentId from the front-end
        if (studentId == null && studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Query the corresponding student record according to studentId
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id", studentId);

        Student student = iStudentService.getOne(studentQueryWrapper);
        if (student == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Get the programId for student
        String programmeId = student.getProgrammeId();
        // Query the corresponding programName according to the programmeId.
        QueryWrapper<Programme> programmeQueryWrapper = new QueryWrapper<>();
        programmeQueryWrapper.eq("programme_id", programmeId);
        Programme programme = iProgrammeService.getOne(programmeQueryWrapper);
        if (programme == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find programme by programmeId");
        }
        String programmeName = programme.getProgrammeName();
        return RespBean.success(programmeName);
    }
    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the description of the programme associated with the given student ID.
     * This method verifies the validity of the provided student ID. If the student ID is empty or null, an error
     * response is returned indicating the inability to find a student with the provided ID. It then queries the
     * corresponding student record using the provided student ID. If no student is found with the provided ID, an error
     * response is returned. Once the student is found, it retrieves the programme ID associated with the student. Using
     * this programme ID, it queries the corresponding programme record. If no programme is found with the retrieved
     * programme ID, an error response is returned.
     *
     * @param studentId The unique identifier of the student for whom the programme description is being requested.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain the description of the programme associated with the student ID. If the student ID is invalid, empty,
     *         or if no student matches the provided ID, an error {@code RespBean} is returned.
     */
    @GetMapping("/programmeDes")
    public RespBean programmeDes(@RequestParam String studentId) {
        // Verify the studentId from the front-end
        if (studentId == null && studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Query the corresponding student record according to studentId
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id", studentId);

        Student student = iStudentService.getOne(studentQueryWrapper);
        if (student == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Get the programId for student
        String programmeId = student.getProgrammeId();
        // Query the corresponding programDes according to the programmeId.
        QueryWrapper<Programme> programmeQueryWrapper = new QueryWrapper<>();
        programmeQueryWrapper.eq("programme_id", programmeId);
        Programme programme = iProgrammeService.getOne(programmeQueryWrapper);
        if (programme == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find programme by programmeId");
        }
        String programmeDes = programme.getDescription();

        return RespBean.success(programmeDes);
    }

    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the list of module IDs associated with the given student ID.
     * This method verifies the validity of the provided student ID. If the student ID is null or empty, an error
     * response is returned indicating the inability to find a student with the provided ID. It then queries the
     * corresponding student record using the provided student ID. If no student is found with the provided ID, an error
     * response is returned. Once the student is found, it retrieves the programme ID associated with the student. Using
     * this programme ID, it queries the database to fetch the list of module IDs associated with the programme. If no
     * module IDs are found for the programme, an error response is returned.
     *
     * @param studentId The unique identifier of the student for whom the list of module IDs is being requested.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain the list of module IDs associated with the student's programme. If the student ID is invalid, empty,
     *         or if no student matches the provided ID, an error {@code RespBean} is returned.
     */
    @GetMapping("/moduleIdList")
    public RespBean moduleIdList(@RequestParam String studentId) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Query the corresponding student record according to studentId
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id", studentId);

        Student student = iStudentService.getOne(studentQueryWrapper);
        if (student == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Get the programId for student
        String programmeId = student.getProgrammeId();

        List<Object> moduleIdList = iModuleService.listObjs(new QueryWrapper<Module>()
                .select("module_id")
                .eq("programme_id", programmeId));

        if (moduleIdList == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find moduleId list by programmeId");
        }
        return RespBean.success(moduleIdList);

    }

    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the time schedule of modules associated with the given student ID.
     * This method verifies the validity of the provided student ID. If the student ID is null or empty, an error
     * response is returned indicating the inability to find a student with the provided ID. It then retrieves the time
     * schedule of modules associated with the student ID. If no module time schedule is found for the student, an error
     * response is returned.
     *
     * @param studentId The unique identifier of the student for whom the module time schedule is being requested.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain the time schedule of modules associated with the student ID. If the student ID is invalid, empty,
     *         or if no module time schedule is found for the student, an error {@code RespBean} is returned.
     */
    @GetMapping("/moduleTime")
    public RespBean moduleTime(@RequestParam String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Query the corresponding student record according to studentId
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id", studentId);

        Student student = iStudentService.getOne(studentQueryWrapper);
        if (student == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Get the programId for student
        String programmeId = student.getProgrammeId();

        // Query the list of modules corresponding to programId
        List<Module> moduleTimeList = iModuleService.list(new QueryWrapper<Module>()
                .select("module_name", "date","module_id")
                .eq("programme_id", programmeId));

        // Create a list to store the returned data
        List<Map<String, Object>> responseData = new ArrayList<>();

        // Convert each module object to a Map containing a duration field and add it to the list of returned data
        for (Module module : moduleTimeList) {
            Map<String, Object> moduleData = new HashMap<>();
            moduleData.put("moduleName", module.getModuleName());
            moduleData.put("date", module.getDate());
            moduleData.put("moduleId", module.getModuleId());
            if (module.getModuleId().equals("FYP00054")){
                moduleData.put("duration", 2);}
            else {moduleData.put("duration", 1);}
            responseData.add(moduleData);
        }

        return RespBean.success(responseData);
    }



    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the credits of modules associated with the given student ID.
     * This method verifies the validity of the provided student ID. If the student ID is null or empty, an error
     * response is returned indicating the inability to find a student with the provided ID. It then retrieves the
     * credits of modules associated with the student's programme. If no module credits are found for the programme,
     * an error response is returned.
     *
     * @param studentId The unique identifier of the student for whom the module credits are being requested.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain the credits of modules associated with the student's programme. If the student ID is invalid, empty,
     *         or if no module credits are found for the programme, an error {@code RespBean} is returned.
     */
    @GetMapping("/moduleCredits")
    public RespBean moduleCredits(@RequestParam String studentId) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Query the corresponding student record according to studentId
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id", studentId);

        Student student = iStudentService.getOne(studentQueryWrapper);
        if (student == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Get the programId for student
        String programmeId = student.getProgrammeId();

        // Query the list of modules corresponding to programId
        List<Module> moduleCreditsList = iModuleService.list(new QueryWrapper<Module>()
                .select("module_name", "credits")
                .eq("programme_id", programmeId));

        if (moduleCreditsList.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find module credits list by programmeId");
        }

        return RespBean.success(moduleCreditsList);

    }

    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the overview of modules associated with the given student ID.
     * This method verifies the validity of the provided student ID. If the student ID is null or empty, an error
     * response is returned indicating the inability to find a student with the provided ID. It then retrieves the
     * overview of modules associated with the student's programme. If no module overviews are found for the programme,
     * an error response is returned.
     *
     * @param studentId The unique identifier of the student for whom the module overviews are being requested.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain the overview of modules associated with the student's programme. If the student ID is invalid, empty,
     *         or if no module overviews are found for the programme, an error {@code RespBean} is returned.
     */
    @GetMapping("/moduleOverview")
    public RespBean moduleOverview(@RequestParam String studentId) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Query the corresponding student record according to studentId
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id", studentId);

        Student student = iStudentService.getOne(studentQueryWrapper);
        if (student == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Get the programId for student
        String programmeId = student.getProgrammeId();

        List<Object> moduleIdOverviewList = iModuleService.listObjs(new QueryWrapper<Module>()
                .select("overview")
                .eq("programme_id", programmeId));

        if (moduleIdOverviewList == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find module overview by programmeId");
        }
        return RespBean.success(moduleIdOverviewList);
    }

    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the names of modules associated with the given student ID.
     * This method verifies the validity of the provided student ID. If the student ID is null or empty, an error
     * response is returned indicating the inability to find a student with the provided ID. It then retrieves the
     * names of modules associated with the student's programme. If no module names are found for the programme,
     * an error response is returned.
     *
     * @param studentId The unique identifier of the student for whom the module names are being requested.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain the names of modules associated with the student's programme. If the student ID is invalid, empty,
     *         or if no module names are found for the programme, an error {@code RespBean} is returned.
     */
    @GetMapping("/moduleName")
    public RespBean moduleName(@RequestParam String studentId) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Query the corresponding student record according to studentId
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id", studentId);

        Student student = iStudentService.getOne(studentQueryWrapper);
        if (student == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Get the programId for student
        String programmeId = student.getProgrammeId();

        List<Object> moduleIdNameList = iModuleService.listObjs(new QueryWrapper<Module>()
                .select("module_name")
                .eq("programme_id", programmeId));

        if (moduleIdNameList == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find module name by programmeId");
        }
        return RespBean.success(moduleIdNameList);
    }

    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the release time and deadline of coursework associated with the given student ID.
     * This method verifies the validity of the provided student ID. If the student ID is null or empty, an error
     * response is returned indicating the inability to find a student with the provided ID. It then retrieves the
     * release time and deadline of coursework associated with the student's programme. If no coursework records are
     * found for the programme, an error response is returned.
     *
     * @param studentId The unique identifier of the student for whom the coursework time information is being requested.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain the release time and deadline of coursework associated with the student's programme. If the
     *         student ID is invalid, empty, or if no coursework records are found for the programme, an error
     *         {@code RespBean} is returned.
     */
    @GetMapping("/courseworkTime")
    public RespBean courseworkTime(@RequestParam String studentId) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }

        // Query the corresponding student record according to studentId
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id", studentId);
        Student student = iStudentService.getOne(studentQueryWrapper);
        if (student == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }

        String programmeId = student.getProgrammeId();

        // Query the list of modules corresponding to the programmeId
        List<Module> moduleList = iModuleService.list(new QueryWrapper<Module>().eq("programme_id", programmeId));
        if (moduleList.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find modules by programmeId");
        }

        List<Coursework> courseworkTimeList = new ArrayList<>();

        // Iterate over each module to query coursework records
        for (Module module : moduleList) {
            String moduleId = module.getModuleId();

            // Query the list of coursework corresponding to the moduleId
            List<Coursework> CourseworkList = iCourseworkService.list(new QueryWrapper<Coursework>()
                    .select("release_time", "deadline")
                    .eq("module_id", moduleId));

            courseworkTimeList.addAll(CourseworkList);
        }

        if (courseworkTimeList.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find coursework by moduleId");
        }

        return RespBean.success(courseworkTimeList);
    }

    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the marks of coursework associated with the given student ID.
     * This method verifies the validity of the provided student ID. If the student ID is null or empty, an error
     * response is returned indicating the inability to find a student with the provided ID. It then retrieves the
     * marks of coursework associated with the student. If no coursework records are found for the student, an error
     * response is returned.
     *
     * @param studentId The unique identifier of the student for whom the coursework marks are being requested.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain the marks of coursework associated with the student. If the student ID is invalid, empty, or if
     *         no coursework records are found for the student, an error {@code RespBean} is returned.
     */
    @GetMapping("/courseworkMark")
    public RespBean courseworkMark(@RequestParam String studentId) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }

        // Query the student coursework records by studentId
        List<CourseworkStudent> courseworkStudentList = iCourseworkStudentService.list(new QueryWrapper<CourseworkStudent>()
                .eq("student_id", studentId));
        if (courseworkStudentList.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find coursework by studentId");
        }

        // Create a list to hold the marks
        List<Double> marks = new ArrayList<>();

        // Iterate over each student coursework record to retrieve the marks
        for (CourseworkStudent courseworkStudent : courseworkStudentList) {
            // Retrieve the mark from the coursework student record
            double mark = courseworkStudent.getMark();

            // Add the mark to the list
            marks.add(mark);
        }

        if (marks.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find coursework mark by studentId " );
        }

        return RespBean.success(marks);
    }

    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the descriptions of coursework for a given student ID.
     * This method retrieves the coursework descriptions associated with the student identified by the provided student ID.
     * It first verifies the validity of the student ID. If the student ID is null or empty, an error response is returned
     * indicating the inability to find a student with the provided ID. It then queries the database to find the corresponding
     * student record. If no student is found with the provided ID, an error response is returned.
     **/
    @GetMapping("/courseworkDes")
    public RespBean courseworkDes(@RequestParam String studentId) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }

        // Query the corresponding student record according to studentId
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id", studentId);
        Student student = iStudentService.getOne(studentQueryWrapper);
        if (student == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }

        String programmeId = student.getProgrammeId();

        // Query the list of modules corresponding to the programmeId
        List<Module> moduleList = iModuleService.list(new QueryWrapper<Module>().eq("programme_id", programmeId));
        if (moduleList.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find modules by programmeId");
        }

        List<Coursework> courseworkDesList = new ArrayList<>();

        // Iterate over each module to query coursework records
        for (Module module : moduleList) {
            String moduleId = module.getModuleId();

            // Query the list of coursework corresponding to the moduleId
            List<Coursework> CourseworkList = iCourseworkService.list(new QueryWrapper<Coursework>()
                    .select("coursework_description")
                    .eq("module_id", moduleId));

            courseworkDesList.addAll(CourseworkList);
        }
        return RespBean.success(courseworkDesList);
    }
    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Uploads coursework file.
     * This method receives a multipart file containing the coursework data. If the uploaded file is empty, an error
     * response is returned indicating the empty file. Otherwise, the uploaded file is processed to store the coursework
     * data. Upon successful processing, a success response is returned.
     *
     * @param file The coursework file to be uploaded.
     * @return {@code RespBean} object containing the operation's result. If successful, an empty success {@code RespBean}
     *         is returned. If the uploaded file is empty, an error {@code RespBean} is returned.
     * @throws IOException If an I/O exception occurs during file processing.
     */
    @PostMapping("/uploadCoursework")
    public RespBean uploadCoursework(@RequestParam MultipartFile file,@RequestParam String studentId,@RequestParam String moduleId) {
        if (file.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Uploaded file is empty");
        }
        CourseworkStudent courseworkStudent = new CourseworkStudent();
        String uuid = UUID.randomUUID().toString();
        courseworkStudent.setCourseworkId(uuid);
        courseworkStudent.setStudentId(studentId);
        courseworkStudent.setModuleId(moduleId);
        courseworkStudent.setIfSubmitted(true);
        courseworkStudent.setIfExtended(true);
        iCourseworkStudentService.save(courseworkStudent);
        return RespBean.success();
    }
    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves exam information for modules associated with the given student ID.
     * This method verifies the validity of the provided student ID. If the student ID is null or empty, an error
     * response is returned indicating the inability to find a student with the provided ID. It then retrieves the
     * exam information for modules associated with the student. If no exam information is found for the student,
     * an empty response is returned.
     *
     * @param studentId The unique identifier of the student for whom the exam information is being requested.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain the exam information for modules associated with the student. If the student ID is invalid, empty,
     *         or if no exam information is found for the student, an error {@code RespBean} is returned.
     */

    @GetMapping("/moduleExamInfo")
    public RespBean moduleExamInfo(@RequestParam String studentId) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        List<ModuleExamInfoDTO> moduleExamInfoDTOList = iExamService.getModuleExamInfo(studentId);
        return RespBean.success(moduleExamInfoDTOList);
    }

    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the email addresses of staff members associated with modules enrolled by the given student ID.
     * This method verifies the validity of the provided student ID. If the student ID is null or empty, an error
     * response is returned indicating the inability to find a student with the provided ID. It then retrieves the
     * email addresses of staff members associated with modules enrolled by the student. If no staff email addresses
     * are found for the modules, an error response is returned.
     *
     * @param studentId The unique identifier of the student for whom the staff email addresses are being requested.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain the email addresses of staff members associated with modules enrolled by the student. If the
     *         student ID is invalid, empty, or if no staff email addresses are found for the modules, an error
     *         {@code RespBean} is returned.
     */
    @GetMapping("/findEmail")
    public RespBean findEmail(@RequestParam String studentId) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }

        // Query the corresponding student record according to studentId
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id", studentId);
        Student student = iStudentService.getOne(studentQueryWrapper);
        if (student == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }

        String programmeId = student.getProgrammeId();

        // Query the list of modules corresponding to the programmeId
        List<Module> moduleList = iModuleService.list(new QueryWrapper<Module>().eq("programme_id", programmeId));
        if (moduleList.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find modules by programmeId");
        }

        List<Staff> staffEmailList = new ArrayList<>();

        // Iterate over each module to query coursework records
        for (Module module : moduleList) {
            String staffId = module.getStaffId();

            // Query the list of coursework corresponding to the moduleId
            List<Staff> emailList = iStaffService.list(new QueryWrapper<Staff>()
                    .select("email")
                    .eq("staff_id", staffId));

            staffEmailList.addAll(emailList);
        }

        if (staffEmailList.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find staff email by staffId");
        }
        return RespBean.success(staffEmailList);
    }


    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the marks of exams associated with the given student ID.
     * This method verifies the validity of the provided student ID. If the student ID is null or empty, an error
     * response is returned indicating the inability to find a student with the provided ID. It then retrieves the
     * marks of exams associated with the student. If no exam records are found for the student, an error response
     * is returned.
     *
     * @param studentId The unique identifier of the student for whom the exam marks are being requested.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain the marks of exams associated with the student. If the student ID is invalid, empty, or if
     *         no exam records are found for the student, an error {@code RespBean} is returned.
     */
    @GetMapping("/examMark")
    public RespBean examMark(@RequestParam String studentId) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }

        // Query the student exam records by studentId
        List<ExamStudent> examStudentList = iExamStudentService.list(new QueryWrapper<ExamStudent>()
                .eq("student_id", studentId));
        if (examStudentList.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find exam records by studentId");
        }

        // Create a list to hold the marks
        List<Double> marks = new ArrayList<>();

        // Iterate over each student exam record to retrieve the marks
        for (ExamStudent examStudent : examStudentList) {
            // Retrieve the mark from the exam student record
            double mark = examStudent.getMark();

            // Add the mark to the list
            marks.add(mark);
        }

        if (marks.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "No marks found for studentId: " + studentId);
        }

        return RespBean.success(marks);
    }

    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves information about the tutor associated with the given student ID.
     * This method verifies the validity of the provided student ID. If the student ID is null or empty, an error
     * response is returned indicating the inability to find a student with the provided ID. It then retrieves the
     * information about the tutor associated with the student. If no tutor information is found for the student,
     * an error response is returned.
     *
     * @param studentId The unique identifier of the student for whom the tutor information is being requested.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain the information about the tutor associated with the student. If the student ID is invalid, empty,
     *         or if no tutor information is found for the student, an error {@code RespBean} is returned.
     */
    @GetMapping("/tutorInfo")
    public RespBean tutorInfo(@RequestParam String studentId) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Query the corresponding student record according to studentId
        QueryWrapper<StudentTutor> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id", studentId);

        StudentTutor studentTutor = iStudentTutorService.getOne(studentQueryWrapper);
        if (studentTutor == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find tutor by studentId");
        }
        // Get the programId for student
        String tutorId = studentTutor.getTutorId();

        List<Staff> tutorInfo = iStaffService.list(new QueryWrapper<Staff>()
                .select("staff_id","first_name", "surname", "email", "background")
                .eq("staff_id", tutorId));

        if (tutorInfo == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find tutor information by staffId");
        }
        return RespBean.success(tutorInfo);

    }

    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the year of study of the student associated with the given student ID.
     * This method verifies the validity of the provided student ID. If the student ID is null or empty, an error
     * response is returned indicating the inability to find a student with the provided ID. It then retrieves the
     * year of study of the student associated with the student ID. If the student year is invalid (not between 0
     * and 4), an error response is returned.
     *
     * @param studentId The unique identifier of the student for whom the year of study is being requested.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain the year of study of the student. If the student ID is invalid, empty, or if the student year
     *         is invalid, an error {@code RespBean} is returned.
     */
    @GetMapping("/studentYear")
    public RespBean studentYear(@RequestParam String studentId) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id", studentId);

        Student student = iStudentService.getOne(studentQueryWrapper);
        int studentYear = student.getStudentYear();
        if (studentYear < 0 || studentYear > 4) {
            return RespBean.error(RespBeanEnum.ERROR,"invalid student year");
        }
        return RespBean.success(studentYear);
    }

    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the study record of the student associated with the given student ID with pagination.
     * This method verifies the validity of the provided student ID. If the student ID is null or empty, an error
     * response is returned indicating the inability to find a student with the provided ID. It then retrieves the
     * study record of the student associated with the student ID. The study record is returned with pagination,
     * with default values of page number as 1 and page size as 10 if not specified.
     *
     * @param studentId The unique identifier of the student for whom the study record is being requested.
     * @param pageNum   The page number for pagination. Defaults to 1 if not specified.
     * @param pageSize  The size of each page for pagination. Defaults to 10 if not specified.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         contain the study record of the student with pagination. If the student ID is invalid, empty, or if
     *         there is an error retrieving the study record, an error {@code RespBean} is returned.
     */
    @GetMapping("/studyRecord")
    public RespBean studyRecord(@RequestParam String studentId,
                                @RequestParam(defaultValue = "1") int pageNum,
                                @RequestParam(defaultValue = "10") int pageSize) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }

        // Construct Page object
        Page<StudentStudyRecordDTO> page = new Page<>(pageNum, pageSize);

        // Calling a method with paging to get a study record
        IPage<StudentStudyRecordDTO> studyRecordPage = iModuleService.getStudyRecordWithPagination(studentId, page);

        // Return paging results
        return RespBean.success(studyRecordPage);
    }

    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Submits an email address for processing.
     * This method checks the format of the submitted email address. If the email address is null or does not match
     * the required format, an error response is returned indicating an invalid email format. Otherwise, a success
     * response is returned.
     *
     * @param email The email address to be submitted for processing.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         indicate successful submission. If the email address format is invalid, an error {@code RespBean} is returned.
     */
    @PostMapping("/submitEmail")
    public RespBean submitEmail(@RequestParam String email){
        // Check the mailbox format
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
            return RespBean.error(RespBeanEnum.ERROR,"Invalid email format");
        }
        return RespBean.success();
    }
    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Uploads text content for processing.
     * This method checks if the submitted text content is empty. If the text content is null or empty, an error
     * response is returned indicating that some text should be provided for upload. Otherwise, the text content is
     * saved to a file with a default name ("uploadedText.txt"). Upon successful upload, a success response is returned.
     *
     * @param request The {@code Text} object containing the text content to be uploaded.
     * @return {@code RespBean} object containing the operation's result. If successful, the {@code RespBean} will
     *         indicate successful upload. If the submitted text is empty, an error {@code RespBean} is returned.
     */
    @PostMapping("/uploadAbsence")
    public RespBean uploadAbsence(@RequestBody Absence request,@RequestParam String studentId){

        String reason = request.getReason();
        String startTime = request.getStartTime();
        String endTime = request.getEndTime();

        if (reason == null || startTime == null || endTime == null) {
            return RespBean.error(RespBeanEnum.ERROR, "please upload absence time and reason");
        }
        // Create a new Absence object to store the text
        Absence absence = new Absence();
        String absenceId = UUID.randomUUID().toString();
        absence.setAbsenceId(absenceId);
        absence.setReason(reason);
        absence.setStudentId(studentId); // Set the studentId
        absence.setStartTime(startTime);
        absence.setEndTime(endTime);

        iAbsenceService.save(absence);

        return RespBean.success("Text uploaded to Absence table successfully.");
    }

    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the type of a student based on the provided student ID.
     * This method fetches the student type from the database based on the given student ID. It first verifies
     * the validity of the student ID provided from the front-end. If the student ID is null or empty, it returns
     * an error response indicating that the student ID cannot be empty. Otherwise, it queries the database to
     * find the student with the provided ID and retrieves their student type. Upon successful retrieval, a success
     * response containing the student type is returned.
     *
     * @param studentId The unique identifier of the student.
     * @return A {@code RespBean} object containing the student type if the operation is successful,
     *         or an error response if the student ID is not found or invalid.
     */
    @GetMapping("/studentType")
    public RespBean studentType(@RequestParam String studentId) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id", studentId);

        Student student = iStudentService.getOne(studentQueryWrapper);
        int studentType = student.getStudentType();

        return RespBean.success(studentType);
    }
    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Retrieves the absence record for a student based on the provided student ID.
     * This method fetches the absence record from the database based on the given student ID. It first verifies
     * the validity of the student ID provided from the front-end. If the student ID is null or empty, it returns
     * an error response indicating that the student ID cannot be empty. Otherwise, it queries the database to
     * find the absence records associated with the provided student ID. Upon successful retrieval, a success
     * response containing the absence record is returned.
     *
     * @param studentId The unique identifier of the student.
     * @return A {@code RespBean} object containing the absence record if the operation is successful,
     *         or an error response if the student ID is not found or invalid.
     */
    @GetMapping("/absenceRecord")
    public RespBean absenceRecord(@RequestParam String studentId){
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }

        // Get the student's leave record
        List<Absence> absenceRecord = iAbsenceService.list(new QueryWrapper<Absence>()
                .select("absence_id","start_time","end_time", "reason", "status","staff_id")
                .eq("student_id", studentId));

        // If there is no leave record, the corresponding error message is returned
        if (absenceRecord.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find absence record by studentId");
        }

        // Update the absenceId and staffId fields for each leave record.
        for (Absence absence : absenceRecord) {
            String uuid = UUID.randomUUID().toString();
            StudentTutor findTutorByStudent = iStudentTutorService.getById(studentId);
            String tutorId = findTutorByStudent.getTutorId();
            absence.setStaffId(tutorId);
            absence.setStatus(0);
            iAbsenceService.updateById(absence);
        }

        // Return to the list of leave records
        return RespBean.success(absenceRecord);
    }
    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Updates the programme choice for a student.
     * This method allows a student to choose their programme by updating the programme ID associated with their record.
     * It first verifies the validity of the student ID and programme ID provided from the front-end. If either of them
     * is null or empty, it returns an error response indicating that the corresponding ID cannot be empty. Then, it
     * queries the database to find the student record with the provided student ID. If the student record is found,
     * it updates the programme ID field with the provided programme ID and saves the updated student record. Upon
     * successful update, a success response is returned. If the update fails, an error response is returned.
     *
     * @param studentId The unique identifier of the student.
     * @param programmeId The unique identifier of the programme chosen by the student.
     * @return A {@code RespBean} object indicating the result of the update operation. If successful, a success response
     *         is returned. If the update fails or the student ID cannot be found, an error response is returned.
     */
    @PutMapping("/choseProgramme")
    public RespBean choseProgramme(@RequestParam String studentId, @RequestParam String programmeId) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Verify the programmeId from the front-end
        if (programmeId == null || programmeId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Programme ID cannot be empty");
        }
        // Queries the student record with the given studentId.
        Student student = iStudentService.getById(studentId);
        if (student == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Update programmeId field
        student.setProgrammeId(programmeId);
        // Saving updated student records
        boolean updated = iStudentService.updateById(student);
        if (updated) {
            return RespBean.success();
        } else {
            return RespBean.error(RespBeanEnum.ERROR, "Failed to update student programme");
        }
    }
    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Updates the programme status for a student.
     * This method handles the update of the programme status for a student identified by the provided student ID.
     * It verifies the student ID from the front-end and queries the student record with the given ID.
     * If the student is found, it updates the programme status field with the provided value.
     * Finally, it saves the updated student record and returns a success response if the update is successful,
     * otherwise, it returns an error response.
     *
     * @param studentId The unique identifier of the student.
     * @param programmeStatus The new programme status to be updated.
     * @return A {@code RespBean} object indicating the result of the update operation.
     */
    @PutMapping("/updateProgrammeStatus")
    public RespBean updateProgrammeStatus(@RequestParam String studentId, @RequestParam int programmeStatus) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }

        // Queries the student record with the given studentId.
        Student student = iStudentService.getById(studentId);
        if (student == null) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");
        }
        // Update programmeId field
        student.setProgrammeStatus(programmeStatus);
        // Saving updated student records
        boolean updated = iStudentService.updateById(student);
        if (updated) {
            return RespBean.success();
        } else {
            return RespBean.error(RespBeanEnum.ERROR, "Failed to update programme status");
        }
    }
    /**
     * Authorization: Student
     * @Author: Xuezhu Chen
     *
     * Submits the exam for a student.
     * This method handles the submission of the exam for a student identified by the provided student ID.
     * It verifies the student ID from the front-end and updates the exam status for the student.
     * If the student is found and the exam status is updated successfully, it returns a success response.
     * Otherwise, it returns an error response.
     *
     * @param studentId The unique identifier of the student.
     * @return A {@code RespBean} object indicating the result of the submission operation.
     */
    @PutMapping("/submitExam")
    public RespBean submitExam(@RequestParam String studentId,@RequestParam String moduleId) {
        // Verify the studentId from the front-end
        if (studentId == null || studentId.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find student by studentId");}
        // Update Exam Status
        ExamStudent examStudent = new ExamStudent();
        examStudent.setIfAttended(true);
        examStudent.setStudentId(studentId);
        examStudent.setModuleId(moduleId);
        String uuid = UUID.randomUUID().toString();
        examStudent.setExamId(uuid);
        // Set the current date as exam date
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        String formattedDate = currentDate.format(formatter);
        examStudent.setExamDate(formattedDate);
        iExamStudentService.save(examStudent);
        return RespBean.success("Submission Success");
    }

    /**
     * @Authroization: Student
     * @Author: Ronghui Zhong
     *
     * Retrieves the academic history for a given student based on the student ID.
     * This method fetches the student details and their module histories from the database.
     * @param studentId
     * @return
     */
    @GetMapping("/getMyAcademicHistory")
    public RespBean getMyAcademicHistory(@RequestParam String studentId){
        if (studentId == null)
            return RespBean.error(RespBeanEnum.ERROR);
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("student_id",studentId);
        Student student = studentMapper.selectOne(studentQueryWrapper);
        if (student == null)
            return RespBean.error(RespBeanEnum.ERROR);
        QueryWrapper<Module> moduleQueryWrapper = new QueryWrapper<>();
        moduleQueryWrapper.eq("programme_id",student.getProgrammeId());
        List<Module> modules = moduleMapper.selectList(moduleQueryWrapper);
        List<ModuleHistory> moduleHistories = new ArrayList<>();
        for (Module module : modules) {
            QueryWrapper<ModuleHistory> moduleHistoryQueryWrapper = new QueryWrapper<>();
            moduleHistoryQueryWrapper.eq("module_id", module.getModuleId());
            moduleHistoryQueryWrapper.eq("student_id",studentId);
            ModuleHistory moduleHistory = moduleHistoryMapper.selectOne(moduleHistoryQueryWrapper);
            if (moduleHistory != null) {
                moduleHistories.add(moduleHistory);
            }
        }
        return RespBean.success(moduleHistories);
    }
}