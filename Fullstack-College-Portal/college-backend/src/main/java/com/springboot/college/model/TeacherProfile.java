package com.springboot.college.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "teacher_profiles")
@Data
public class TeacherProfile {

    // --- 1. GIVE IT ITS OWN ID ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- 2. CHANGE THE MAPPING ---
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    @JsonIgnore
    private User user;

    private String name;
    private String department;
    private String personalData;
}