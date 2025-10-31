package com.springboot.college.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "student_profiles")
@Data
public class StudentProfile {

    // --- 1. GIVE IT ITS OWN ID ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- 2. CHANGE THE MAPPING ---
    // User is now linked by a new 'user_id' column
    // We also add CascadeType.ALL
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;

    private String name;
    private String batch;
    private int year;
    private String personalData;

    @JsonManagedReference
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Result> results;
}