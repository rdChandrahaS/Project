package com.springboot.college.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.college.model.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {}