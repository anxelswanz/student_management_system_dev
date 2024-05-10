package com.group1.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Ronghui Zhong
 * @description: Entry point for the Dyson Student Management System Backend application.
 * This Spring Boot application sets up the entire backend service for managing
 * student information, including registration, authentication, and various other
 * functionalities specific to the needs of Dyson University.
 * @date 2024/3/29 19:00
 * @ProjectName Dyson Student Management System
 **/
@SpringBootApplication
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

}
