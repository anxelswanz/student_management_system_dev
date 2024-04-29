package com.newcastle_university_group1.student_management_system_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.newcastle_university_group1.student_management_system_backend.entity.Staff;
import com.newcastle_university_group1.student_management_system_backend.entity.StaffProgrammeLink;
import com.newcastle_university_group1.student_management_system_backend.entity.Student;
import com.newcastle_university_group1.student_management_system_backend.mapper.StaffMapper;
import com.newcastle_university_group1.student_management_system_backend.mapper.StudentMapper;
import com.newcastle_university_group1.student_management_system_backend.service.IStaffProgrammeLinkService;
import com.newcastle_university_group1.student_management_system_backend.service.IStaffService;
import com.newcastle_university_group1.student_management_system_backend.service.IStudentService;
import com.newcastle_university_group1.student_management_system_backend.vo.RespBean;
import com.newcastle_university_group1.student_management_system_backend.vo.RespBeanEnum;
import com.newcastle_university_group1.student_management_system_backend.vo.TagsVo;
import com.newcastle_university_group1.student_management_system_backend.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.time.Year;
import java.util.List;

/**
 * @author Ronghui Zhong
 * @description:
 * This class deals with logic and operation of User (Student / Staff)
 * includes :
 * (1) User Login
 * (2) User Register
 * @date 2024/3/12 7:46
 * @ProjectName Dyson Student Management System
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IStudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private IStaffService staffService;

    @Autowired
    private IStaffProgrammeLinkService staffProgrammeLinkService;
    /**
     * Handles user login requests. Authenticates the user based on provided credentials and
     * sets the user session if authentication is successful.
     *
     * @param userVo
     *  - the username of the user attempting to log in
     *  - the password of the user attempting to log in
     *  - a boolean flag indicating if the user opted for the "remember me" feature
     *    to maintain their session across different sessions. Defaults to false if
     *    not explicitly provided.
     * @return RespBean an object representing the outcome of the login attempt. On successful
     *         authentication, it returns a success response populated with the user's details
     *         (either Staff or Student). In case of failure, it returns an error response indicating
     *         the login was unsuccessful.
     */
    @PostMapping("/login")
    public RespBean login( @RequestBody UserVo userVo){
        Subject subject = SecurityUtils.getSubject();
        String name = userVo.getName();
        String password = userVo.getPassword();
        boolean rememberMe = userVo.isRememeberMe();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(name, password,rememberMe);
        Object principal = SecurityUtils.getSubject().getPrincipal();
        try {
            subject.login(usernamePasswordToken);
            if (principal instanceof Staff) {
                Staff staff = (Staff) principal;
                /**
                 * Set password to empty string for security
                 */
                staff.setPassword("");
                return RespBean.success(staff);
            }
            if (principal instanceof Student) {
                Student student = (Student) principal;
                /**
                 * Set password to empty string for security
                 */
                student.setPassword("");
                return RespBean.success(student);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(RespBeanEnum.LOGIN_FAILURE);
        }
        return RespBean.error(RespBeanEnum.LOGIN_FAILURE);
    }

    /**
     * Authorization: Only Admin
     *
     * Registers a new student in the system with a unique student ID and a generated password.
     * The student ID is generated based on the format "S + current year's last two digits + a sequential count".
     * The password is created by combining the last four digits of the student ID and the student's surname,
     * hashed with MD5 for security. The student's year is set to 1, indicating they are in their first year.
     *
     * @param student The {@link Student} object containing the student's details except for the student ID
     *                and password, which are generated by this method.
     * @return {@link RespBean}
     *
     * The registration process involves the following steps:
     * 1. Generating a unique student ID using the current year and a count of students already registered in
     *    the same year.
     * 2. Generating a secure password using the last four digits of the student ID and the student's surname,
     *    hashed with MD5.
     * 3. Setting the student's year to 1.
     * 4. Adding the student's information in the database.
     *
     * The method uses MD5 hashing with a specified salt and iterates the hashing process three times to
     * enhance the security of the generated password.
     */
    @PostMapping("/registerStudent")
    public RespBean registerStu(@RequestBody Student student){

        /**
         *  1. Create Student ID
         *
         *  format: S + Year + Count
         */
        int value = Year.now().getValue();
        String yearStr = Integer.toString(value).substring(2);
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.likeRight("student_id","S"+yearStr);
        Integer count = studentMapper.selectCount(studentQueryWrapper);
        String formatCount = String.format("%04d",count + 1);
        String userId = "S" + yearStr + formatCount;
        student.setStudentId(userId);
        /**
         *  2. Create Student Password
         *
         *  format: last 4 digit numbers + Student Surname
         */
        String password = userId.substring(userId.length() - 4) + student.getSurname();
        Md5Hash md5hash = new Md5Hash(password, "salt", 3);
        student.setPassword(md5hash.toString());

        /**
         * 3. Create Student Year
         */
        student.setStudentYear(1);

        /**
         * 4. create programmeId
         */
        if (student.getStudentType() == 2) {
            student.setProgrammeId("U01");
        } else if (student.getStudentType() == 3){
            student.setProgrammeId("M01");
        } else {
            return RespBean.error(RespBeanEnum.ERROR);
        }
        /**
         * 5. set programme status as 0 ongoing
         * other:
         * 1: withdrawn
         * 2: suspend
         */
        student.setProgrammeStatus(0);
        boolean save = studentService.save(student);
        if (save)
            return RespBean.success();
        else
            return RespBean.error(RespBeanEnum.ERROR);
    }

    /**
     * Authorization: Only Admin
     *
     * Registers a new employee in the system with a unique employee ID and a generated password.
     * The method creates a unique employee ID based on the format "E + current year's last two digits + a sequential count".
     * It then generates a password combining the last four digits of the employee ID and the employee's surname,
     * which is hashed using MD5 for security. The employee's type is set to 1, indicating a teacher.
     *
     * @param staff The {@link Staff}
     * @return {@link RespBean} An object representing the outcome of the registration process.
     *
     * Note: The method uses MD5 hashing with a specified salt and iterates the hashing process three times to
     * enhance the security of the generated password.
     */
    @PostMapping("/registerEmp")
    public RespBean registerEmp(@RequestBody Staff staff){
        /**
         *  1. Create Employee ID
         *
         *  format: E + Year + Count
         */
        int value = Year.now().getValue();
        String yearStr = Integer.toString(value).substring(2);
        QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
        staffQueryWrapper.likeRight("staff_id","E"+yearStr);
        Integer count = staffMapper.selectCount(staffQueryWrapper);
        String formatCount = String.format("%04d",count + 1);
        String userId = "S" + yearStr + formatCount;
        staff.setStaffId(userId);

        /**
         *  2. Create Employee Password
         *
         *  format: last 4 digit numbers + Staff Surname
         */
        String password = userId.substring(userId.length() - 4) + staff.getSurname();
        Md5Hash md5hash = new Md5Hash(password, "salt", 3);
        staff.setPassword(md5hash.toString());

        /**
         *  3. Create Employee type
         *
         *  1 : Teacher
         */
        staff.setStaffType(1);

        /**
         *  4. Create Tags
         */
        List<TagsVo> tags = staff.getTags();
        StaffProgrammeLink staffProgrammeLink = new StaffProgrammeLink();
        staffProgrammeLink.setStaffId(userId);
        for (TagsVo tag : tags) {
            String id = tag.getId();
            staffProgrammeLink.setProgrammeId(id);
            staffProgrammeLinkService.save(staffProgrammeLink);
        }
        boolean save = staffService.save(staff);
        if (save)
            return RespBean.success();
        else
            return RespBean.error(RespBeanEnum.ERROR);
    }

    @RequestMapping("/index")
    public RespBean index(){
        return RespBean.error(RespBeanEnum.NOT_AUTHENTICATED);
    }

}
