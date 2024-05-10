package com.group1.system.vo;

import com.group1.system.entity.Timetable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Jingwei Luo
 * @description: Represents a composite view object encapsulating various details about
 * a module necessary for viewing or managing its timetable.
 * @date 2024/5/9 10:00
 * @ProjectName Dyson Student Management System
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleTimetableVO  {

    private String programmeId;

    /**
     *  Module Id
     */
    private String moduleId;

    /**
     *  Credits
     */
    private Integer credits;

    /**
     *  Module Name
     */
    private String moduleName;

    /**
     *  Date
     */
    private String date;

    /**
     *  Staff Id
     */
    private String staffId;

    /**
     *  Type
     */
    private String type;

    /**
     *  Count
     */
    private Integer count;

    /**
     * 0: Already ended, 1: Ongoing, 2:Upcoming
     */
    private Integer status;

    private List<Timetable> timetables;


}
