package com.springboot.college.repository; 

import com.springboot.college.model.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; 

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    
    // ADD THIS LINE:
    Optional<StudentProfile> findByUser_Username(String username);

}