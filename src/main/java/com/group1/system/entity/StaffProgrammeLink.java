package com.group1.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
/**
 * @author Ronghui Zhong
 * @description: Staff Programme Link Class
 * @date 2024/4/3 21:01
 * @ProjectName Dyson Student Management System
 **/
@TableName("staff_programme_link")
public class StaffProgrammeLink implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Staff Id
     */
    private String staffId;

    private String programmeId;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    @Override
    public String toString() {
        return "StaffProgrammeLink{" +
            "staffId=" + staffId +
            ", programmeId=" + programmeId +
        "}";
    }
}
