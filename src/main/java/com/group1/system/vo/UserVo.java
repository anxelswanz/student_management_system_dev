package com.group1.system.vo;

/**
 * @author Ronghui Zhong
 * @description: UserVo for UserLogin
 * @date 2024/4/26 17:18
 * @ProjectName student_management_system_backend
 **/
public class UserVo {
    private String name;
    private String password;
    private boolean rememeberMe;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememeberMe() {
        return rememeberMe;
    }

    public void setRememeberMe(boolean rememeberMe) {
        this.rememeberMe = rememeberMe;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", rememeberMe=" + rememeberMe +
                '}';
    }
}
