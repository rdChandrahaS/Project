package com.springboot.college.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.college.model.Result; /* Results */
import com.springboot.college.model.StudentProfile; /* Profile */
import com.springboot.college.repository.StudentProfileRepository; /* Repo */

@RestController
@RequestMapping("/api/student")
@PreAuthorize("hasRole('STUDENT')") /* Only accessable by Students */
public class StudentController {

    @Autowired
    private StudentProfileRepository studentProfileRepository;
    
    @GetMapping("/profile")
    public ResponseEntity<?> getStudentProfile(Principal principal) {
        /* Finding the profile linked to the logged in user's username */
        Optional<StudentProfile> profileOpt = studentProfileRepository.findByUser_Username(principal.getName());

        if (profileOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Profile not found for user: " + principal.getName()); /* If Student not exist */
        }

        /* Returning the found profile */
        return ResponseEntity.ok(profileOpt.get());
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateStudentProfile(@RequestBody StudentProfile updatedProfile, Principal principal) {
        /* Finding the existing profile */
        Optional<StudentProfile> profileOpt = studentProfileRepository.findByUser_Username(principal.getName());

        if (profileOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Profile not found for user: " + principal.getName());
        }

        StudentProfile existingProfile = profileOpt.get();

        /* Update only the fields that a student is allowed to change 
         * Name
         * Batch
         * Year
         * Personal data
        */
        existingProfile.setName(updatedProfile.getName());
        existingProfile.setBatch(updatedProfile.getBatch());
        existingProfile.setYear(updatedProfile.getYear());
        existingProfile.setPersonalData(updatedProfile.getPersonalData());

        /* Save the updated profile back to the database */
        StudentProfile savedProfile = studentProfileRepository.save(existingProfile);
        return ResponseEntity.ok(savedProfile);
    }

    @GetMapping("/results")
    public ResponseEntity<?> getStudentResults(Principal principal) {
        /* Find the profile */
        Optional<StudentProfile> profileOpt = studentProfileRepository.findByUser_Username(principal.getName());

        if (profileOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Profile not found for user: " + principal.getName());
        }

        /* Get the results list from the profile and return it */
        List<Result> results = profileOpt.get().getResults();
        return ResponseEntity.ok(results);
    }
}