package com.group1.system.realm;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.group1.system.entity.Staff;
import com.group1.system.entity.Student;
import com.group1.system.mapper.StaffMapper;
import com.group1.system.mapper.StudentMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Ronghui Zhong
 * @description: This class belongs to the Shiro Framework
 * Function:
 * fetch data from the database to conduct following tasks
 * (1) Authentication
 * (2) Authorization
 * @date 2024/3/29 20:42
 * @ProjectName Dyson Student Management System
 **/

public class MyRealm extends AuthorizingRealm {


    @Autowired
    private StudentMapper studentMapper;


    @Autowired
    private StaffMapper staffMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * Authentication
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        /**
         *  Get the user information
         */
        String username = authenticationToken.getPrincipal().toString();
        QueryWrapper queryWrapper = new QueryWrapper();

        /**
         *  If the user is an employee / admin
         */
        Object principle;
        if (username.substring(0,1).equals("E")) {
            queryWrapper.eq("staff_id", username);
            Staff staff = staffMapper.selectOne(queryWrapper);
            if (staff != null) {
                principle = staff;
                return new SimpleAuthenticationInfo(
                        principle,
                        staff.getPassword(),
                        ByteSource.Util.bytes("salt"),
                        username
                );
            }
        }
        /**
         *  If the user is a student
         */
        else {
            queryWrapper.eq("student_id", username);
            Student student = studentMapper.selectOne(queryWrapper);
            principle = student;
            if (student != null) {
                return new SimpleAuthenticationInfo(
                        principle,
                        student.getPassword(),
                        ByteSource.Util.bytes("salt"),
                        username
                );
            }
        }

        return null;
    }
}
