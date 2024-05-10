package com.group1.system.dto;

/**
 * @author Ronghui Zhong
 * @Description: The Encapsulation of the data
 * @date 2024/4/30 18:24
 * @ProjectName student_management_system_backend
 **/
public class SubmittedWorkDTO {
    private String moduleId;
    private String type;
    private String submittedStatus;
    private String studentId;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubmittedStatus() {
        return submittedStatus;
    }

    public void setSubmittedStatus(String submittedStatus) {
        this.submittedStatus = submittedStatus;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }


    @Override
    public String toString() {
        return "SubmittedWorkDto{" +
                "moduleId='" + moduleId + '\'' +
                ", type='" + type + '\'' +
                ", submittedStatus='" + submittedStatus + '\'' +
                ", studentId='" + studentId + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
