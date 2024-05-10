package com.group1.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.group1.system.vo.TagsVo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ronghui Zhong
 * @description: Staff Entity Class
 * @date 2024/3/29 22:10
 * @ProjectName Dyson Student Management System
 **/
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Staff ID
     */
    private String staffId;

    /**
     * Staff surname
     */
    private String surname;

    /**
     * Staff firstname
     */
    private String firstName;

    /**
     * Staff Type ( 0: Admin, 1: teacher)
     */
    private Integer staffType;


    /**
     *  Password
     */
    private String password;

    /**
     * email
     */
    private String email;

    /**
     * Staff Background
     */
    private String background;

    /**
     * Module Ids
     */
    @TableField(exist = false)
    private List<TagsVo> tags;

    public List<TagsVo> getTags() {
        return tags;
    }

    public void setTags(List<TagsVo> tags) {
        this.tags = tags;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getStaffType() {
        return staffType;
    }

    public void setStaffType(Integer staffType) {
        this.staffType = staffType;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffId='" + staffId + '\'' +
                ", surname='" + surname + '\'' +
                ", firstName='" + firstName + '\'' +
                ", staffType=" + staffType +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", background='" + background + '\'' +
                ", moduleIds=" + tags +
                '}';
    }
}
