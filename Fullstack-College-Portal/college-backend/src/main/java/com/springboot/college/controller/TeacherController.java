package com.springboot.college.controller;

import java.security.Principal;
import java.util.Optional;
import java.util.Set; /* Import all models */

import org.springframework.beans.factory.annotation.Autowired; /* Import all repos */
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.college.dto.ResultUpdateDto;
import com.springboot.college.dto.StudentRegistrationDto;
import com.springboot.college.model.Result;
import com.springboot.college.model.Role;
import com.springboot.college.model.StudentProfile;
import com.springboot.college.model.TeacherProfile;
import com.springboot.college.model.User;
import com.springboot.college.repository.ResultRepository;
import com.springboot.college.repository.RoleRepository;
import com.springboot.college.repository.StudentProfileRepository;
import com.springboot.college.repository.TeacherProfileRepository;
import com.springboot.college.repository.UserRepository;

@RestController
@RequestMapping("/api/teacher")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherController {

    @Autowired
    private StudentProfileRepository studentProfileRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeacherProfileRepository teacherProfileRepository;

    /* Updating Result */
    @PostMapping("/results/update")
    public ResponseEntity<?> updateStudentResult(@RequestBody ResultUpdateDto resultDto) {
        Optional<StudentProfile> studentOpt = studentProfileRepository.findById(resultDto.getStudentId());
        if (studentOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Student not found with ID: " + resultDto.getStudentId());
        }
        StudentProfile student = studentOpt.get();
        Optional<Result> existingResultOpt = student.getResults().stream()
                .filter(r -> r.getSubject().equalsIgnoreCase(resultDto.getSubject()))
                .findFirst();
        Result resultToSave;
        if (existingResultOpt.isPresent()) {
            resultToSave = existingResultOpt.get();
            resultToSave.setMarks(resultDto.getMarks());
        } else {
            resultToSave = new Result();
            resultToSave.setStudent(student);
            resultToSave.setSubject(resultDto.getSubject());
            resultToSave.setMarks(resultDto.getMarks());
        }
        Result savedResult = resultRepository.save(resultToSave);
        return ResponseEntity.ok(savedResult);
    }
    
    /* Creating Result */
    @PostMapping("/create-student")
    public ResponseEntity<?> createStudent(@RequestBody StudentRegistrationDto studentDto) {
        if (userRepository.findByUsername(studentDto.getUsername()).isPresent()) {
            return ResponseEntity.status(400).body("Username (Student ID) already exists");
        }
        Role studentRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseThrow(() -> new RuntimeException("Error: ROLE_STUDENT is not found."));
        User studentUser = new User();
        studentUser.setUsername(studentDto.getUsername());
        studentUser.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        studentUser.setRoles(Set.of(studentRole));
        User savedUser = userRepository.save(studentUser);
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setUser(savedUser);
        studentProfile.setName(studentDto.getName());
        studentProfile.setBatch(studentDto.getBatch());
        studentProfile.setYear(studentDto.getYear());
        studentProfile.setPersonalData(studentDto.getPersonalData());
        studentProfileRepository.save(studentProfile);
        return ResponseEntity.ok("Student created successfully!");
    }

    
    /* Teacher's profile */
    @GetMapping("/profile")
    public ResponseEntity<?> getTeacherProfile(Principal principal) {
        Optional<TeacherProfile> profileOpt = teacherProfileRepository.findByUser_Username(principal.getName());
        if (profileOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Profile not found for user: " + principal.getName());
        }
        return ResponseEntity.ok(profileOpt.get());
    }

    /* Updaten Teachers profile */
    @PutMapping("/profile")
    public ResponseEntity<?> updateTeacherProfile(@RequestBody TeacherProfile updatedProfile, Principal principal) {
        Optional<TeacherProfile> profileOpt = teacherProfileRepository.findByUser_Username(principal.getName());
        if (profileOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Profile not found for user: " + principal.getName());
        }

        TeacherProfile existingProfile = profileOpt.get();
        existingProfile.setName(updatedProfile.getName());
        existingProfile.setDepartment(updatedProfile.getDepartment());
        existingProfile.setPersonalData(updatedProfile.getPersonalData());
        
        TeacherProfile savedProfile = teacherProfileRepository.save(existingProfile);
        return ResponseEntity.ok(savedProfile);
    }
}