package com.springboot.college.dto;

import lombok.Data;

@Data
public class TeacherRegistrationDto {
    // From User
    private String username;
    private String password;
    
    // From TeacherProfile
    private String name;
    private String department;
    private String personalData;
}