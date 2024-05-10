package com.group1.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


/**
 * @author Ronghui Zhong
 * @description: CourseworkStudent Entity Class
 * @date 2024/4/28 18:00
 * @ProjectName Dyson Student Management System
 **/
@Data
public class CourseworkStudent {

    private String studentId;
    @TableId
    private String courseworkId;
    @TableField
    private double mark;
    private boolean ifExtended;
    private boolean ifSubmitted;
    private String moduleId;
}
