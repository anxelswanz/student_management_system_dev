package com.newcastle_university_group1.student_management_system_backend.realm;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.newcastle_university_group1.student_management_system_backend.entity.Staff;
import com.newcastle_university_group1.student_management_system_backend.entity.Student;
import com.newcastle_university_group1.student_management_system_backend.mapper.StaffMapper;
import com.newcastle_university_group1.student_management_system_backend.mapper.StudentMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    /**
     *  Authorization
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        System.out.println("自定义授权方法");
//        //1. 创建对象, 封装当前登录用户信息, 授权信息
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        //2. 存储角色
//        //info.addRole("admin");
//        //拿取身份信息
//        String principle = principalCollection.getPrimaryPrincipal().toString();
//        // 查询数据库
//        List<String> roles = userService.getUserRoleInfo(principle);
//        System.out.println("当前用户角色信息 =>" + roles);
//        //获取用户权限信息
//        List<String> userPermissionInfo = userService.getUserPermissionInfoMapper(roles);
//        System.out.println("当前用户权限信息信息 =>" + userPermissionInfo);
//        info.addStringPermissions(userPermissionInfo);
//        info.addRoles(roles);
//        //3. 返回信息
//        return info;
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
