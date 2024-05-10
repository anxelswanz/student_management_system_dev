package com.group1.system.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Ronghui Zhong
 * @description: Programme Entity Class
 * @date 2024/4/3 21:01
 * @ProjectName Dyson Student Management System
 **/
public class Programme implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Programme ID
     */
    private String programmeId;

    /**
     * Programme Name
     */
    private String programmeName;

    /**
     * Programme Description
     */
    private String description;

    /**
     * The start time of the programme
     */
    private LocalDateTime startTime;

    /**
     * The end time of the programme
     */
    private LocalDateTime endTime;

    /**
     * The total credits of the programme
     */
    private Integer totalCredits;

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }
    public String getProgrammeName() {
        return programmeName;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public Integer getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }

    @Override
    public String toString() {
        return "Programme{" +
            "programmeId=" + programmeId +
            ", programmeName=" + programmeName +
            ", description=" + description +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", totalCredits=" + totalCredits +
        "}";
    }
}
