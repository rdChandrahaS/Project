package com.springboot.college.dto;

import lombok.Data;

@Data
public class ResultUpdateDto {

    private Long studentId;
    private String subject;
    private String marks;
    
}