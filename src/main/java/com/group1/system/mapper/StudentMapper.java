package com.group1.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.group1.system.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Ronghui Zhong
 * @description: StudentMapper Interface for CRUD
 * @date 2024/3/29 21:51
 * @ProjectName Dyson Student Management System
 **/
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Select("SELECT role_name FROM role where role_id in ( select rid from role_user where uid = (select user_id from user where user_name = #{principal})) ")
    List<String> getUserRoleInfoMapper(@Param("principal") String principal);

    @Select("<script> SELECT info from permissions where id in ( select pid from role_ps where rid in ( select role_id from role where role_name in <foreach collection = 'roles' item='name' open='(' separator=',' close=')'> #{name} </foreach> )) </script>")
    List<String> getUserPermissionInfoMapper(@Param("roles") List<String> roles);
}
