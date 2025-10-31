package com.springboot.college.dto;

import lombok.Data;

@Data
public class StudentRegistrationDto {
    // From User
    private String username;
    private String password;
    
    // From StudentProfile
    private String name;
    private String batch;
    private int year;
    private String personalData;
}