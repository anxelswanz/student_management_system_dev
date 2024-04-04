package com.newcastle_university_group1.student_management_system_backend;

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
public class StudentManagementSystemBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemBackendApplication.class, args);
    }

}
