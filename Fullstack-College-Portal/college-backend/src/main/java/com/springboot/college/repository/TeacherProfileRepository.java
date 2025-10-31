package com.springboot.college.repository;

import com.springboot.college.model.TeacherProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TeacherProfileRepository extends JpaRepository<TeacherProfile, Long> {
    // We need this for the teacher to find their own profile
    Optional<TeacherProfile> findByUser_Username(String username);
}