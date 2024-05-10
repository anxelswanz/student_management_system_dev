package com.group1.system.vo;

/**
 * @author Ronghui Zhong
 * @description: Receive Tags from the staff register
 * @date 2024/4/29 12:18
 * @ProjectName student_management_system_backend
 **/
public class TagsVo {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TagsVo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
