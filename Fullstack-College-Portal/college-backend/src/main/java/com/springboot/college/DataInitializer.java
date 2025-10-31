package com.springboot.college;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.springboot.college.model.Role;
import com.springboot.college.model.StudentProfile;
import com.springboot.college.model.TeacherProfile;
import com.springboot.college.model.User;
import com.springboot.college.repository.RoleRepository;
import com.springboot.college.repository.StudentProfileRepository;
import com.springboot.college.repository.TeacherProfileRepository;
import com.springboot.college.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentProfileRepository studentProfileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TeacherProfileRepository teacherProfileRepository; 

    @Override
    public void run(String... args) throws Exception {
        
        // Check if data is already there to avoid duplicates
        if (roleRepository.count() > 0) {
            return;
        }

        System.out.println("--- Initializing new database data ---");
        
        // --- Create Roles ---
        Role studentRole = new Role();
        studentRole.setName("ROLE_STUDENT");
        roleRepository.save(studentRole);

        Role teacherRole = new Role();
        teacherRole.setName("ROLE_TEACHER");
        roleRepository.save(teacherRole);

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        // --- Create Sample Student ---
        // We create both objects first, then link them
        User studentUser = new User();
        studentUser.setUsername("student");
        studentUser.setPassword(passwordEncoder.encode("123"));
        studentUser.setRoles(Set.of(studentRole));
        
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setName("Sample Student");
        studentProfile.setBatch("2025");
        studentProfile.setYear(2);
        studentProfile.setPersonalData("sample.student@college.com");
        
        // Link them
        studentProfile.setUser(studentUser);
        
        // Now, we just save the PROFILE. The User will be saved automatically.
        studentProfileRepository.save(studentProfile);

        // --- Create Sample Teacher ---
        User teacherUser = new User();
        teacherUser.setUsername("teacher");
        teacherUser.setPassword(passwordEncoder.encode("123"));
        teacherUser.setRoles(Set.of(teacherRole));

        TeacherProfile teacherProfile = new TeacherProfile();
        teacherProfile.setName("Prof...");
        teacherProfile.setDepartment("Information Technology");
        teacherProfile.setPersonalData("prof.teacher@college.com");
        
        // Link them
        teacherProfile.setUser(teacherUser);
        
        // Save the PROFILE
        teacherProfileRepository.save(teacherProfile);
        
        // 6. CREATE ADMIN USER
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("123"));
        // Admin gets ONLY the admin role
        adminUser.setRoles(Set.of(adminRole)); // <-- TO THIS
        userRepository.save(adminUser);
        
        // Admin doesn't have a profile, so we just save the user
        userRepository.save(adminUser);

        System.out.println("--- Database Initialized Successfully ---");
        System.out.println("Student Login: student101 / pass123");
        System.out.println("Teacher Login: teacher202 / pass123");
        System.out.println("Admin Login:   admin / adminpass");
    }
}