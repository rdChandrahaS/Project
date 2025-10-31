package com.springboot.college.controller;

import com.springboot.college.dto.TeacherRegistrationDto;
import com.springboot.college.model.Role;
import com.springboot.college.model.TeacherProfile;
import com.springboot.college.model.User;
import com.springboot.college.repository.RoleRepository;
import com.springboot.college.repository.TeacherProfileRepository;
import com.springboot.college.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TeacherProfileRepository teacherProfileRepository;

    @PostMapping("/create-teacher")
    public ResponseEntity<?> createTeacher(@RequestBody TeacherRegistrationDto teacherDto) {
        /*  Checking if username already exists */
        if (userRepository.findByUsername(teacherDto.getUsername()).isPresent()) {
            return ResponseEntity.status(400).body("Username (Teacher ID) already exists");
        }

        /*  Finding the TEACHER role */
        Role teacherRole = roleRepository.findByName("ROLE_TEACHER")
                .orElseThrow(() -> new RuntimeException("Error: ROLE_TEACHER is not found."));

        /* Creating new User */
        User teacherUser = new User();
        teacherUser.setUsername(teacherDto.getUsername());
        teacherUser.setPassword(passwordEncoder.encode(teacherDto.getPassword()));
        teacherUser.setRoles(Set.of(teacherRole));
        User savedUser = userRepository.save(teacherUser);

        /* Creating new Teacher Profile */
        TeacherProfile teacherProfile = new TeacherProfile();
        teacherProfile.setUser(savedUser);
        teacherProfile.setName(teacherDto.getName());
        teacherProfile.setDepartment(teacherDto.getDepartment());
        teacherProfile.setPersonalData(teacherDto.getPersonalData());
        teacherProfileRepository.save(teacherProfile);

        return ResponseEntity.ok("Teacher created successfully!");
    }
}