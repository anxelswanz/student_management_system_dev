package com.group1.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Ronghui Zhong, Jingwei Luo
 * @description: Timetable Entity class
 * @date 2024/4/30 11:16
 * @ProjectName: Dyson Student Management System
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Timetable implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Module Name
     */
    private String moduleName;

    /**
     * Module ID
     */
    private String moduleId;

    /**
     * Class Location
     */
    private String classLocation;

    /**
     * Programme ID
     */
    private String programmeId;

    /**
     * Module Time: 1 - 5 stands for Monday to Friday, 1-1 stands for Monday morning, 1-2 stands for Monday afternoon, we assume that morning class is from 9 - 11, afternoon class is from 3-5
     */
    private String moduleTime;

    /**
     * Module Year
     */
    private String year;

}
