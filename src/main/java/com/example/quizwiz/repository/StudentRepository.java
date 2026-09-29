
package com.example.quizwiz.repository;

import com.example.quizwiz.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    // Find student using email
    Optional<Student> findByEmail(String email);

    // Find student using register number
    Optional<Student> findByRegisterNumber(String registerNumber);

    // Check whether email already exists
    boolean existsByEmail(String email);

    // Check whether register number already exists
    boolean existsByRegisterNumber(String registerNumber);
}

