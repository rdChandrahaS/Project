package com.springboot.college.repository; 

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
 
import com.springboot.college.model.StudentProfile; 

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    
    Optional<StudentProfile> findByUser_Username(String username);

}