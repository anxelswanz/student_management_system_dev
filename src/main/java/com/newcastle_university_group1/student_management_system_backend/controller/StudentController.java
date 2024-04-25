package com.newcastle_university_group1.student_management_system_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newcastle_university_group1.student_management_system_backend.dto.ModuleExamInfoDTO;
import com.newcastle_university_group1.student_management_system_backend.dto.StudentStudyRecordDTO;
import com.newcastle_university_group1.student_management_system_backend.entity.*;
import com.newcastle_university_group1.student_management_system_backend.mapper.ModuleMapper;
import com.newcastle_university_group1.student_management_system_backend.mapper.StudentMapper;
import com.newcastle_university_group1.student_management_system_backend.service.*;
import com.newcastle_university_group1.student_management_system_backend.vo.RespBean;
import com.newcastle_university_group1.student_management_system_backend.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ronghui Zhong & Xuezhu Chen
 * @description:
 * @date 2024/4/5 2:58
 * @ProjectName student_management_system_backend
 **/
@RestController
@RequestMapping("/student")
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
        if (studentId == "" && studentId.isEmpty()) {
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
        if (studentId == "" && studentId.isEmpty()) {
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
        List<Module> moduleTimeList = iModuleService.getModuleTime(studentId);
        if (moduleTimeList == null || moduleTimeList.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Cannot find module time by studentId");
        }
        return RespBean.success(moduleTimeList);
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
    public RespBean uploadCoursework(@RequestParam MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return RespBean.error(RespBeanEnum.ERROR, "Uploaded file is empty");
        }
        iCourseworkService.getUploadCoursework(file);
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
                .select("first_name", "surname", "email", "background")
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
}
