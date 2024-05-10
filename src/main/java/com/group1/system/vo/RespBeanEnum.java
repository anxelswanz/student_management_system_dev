package com.group1.system.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Ronghui Zhong
 * @description: This class belongs to the Shiro Framework
 * Function: Enumerate Class for returning message to the front-end
 * @date 2024/3/31 21:30
 * @ProjectName Dyson Student Management System
 **/
@ToString
@Getter
@AllArgsConstructor
public enum RespBeanEnum {
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"SERVICE FAILURE"),
    /**
     *  User State Code
     */
    LOGIN_FAILURE(401, "LOGIN FAILURE"),
    /**
     * No authorization
     */
    NOT_AUTHORIZED(403,"NOT AUTHORIZED"),
    /**
     * No authentication
     */
    NOT_AUTHENTICATED(401, "NOT AUTHENTICATED"),

    ;
    /**
     * State Code
     */
    private final Integer code;
    /**
     *  Message
     */
    private final String message;
}
