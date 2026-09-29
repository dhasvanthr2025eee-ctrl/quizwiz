
package com.example.quizwiz.controller;

import com.example.quizwiz.dto.StudentRequest;
import com.example.quizwiz.entity.Student;
import com.example.quizwiz.repository.StudentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin
public class StudentController {

    private final StudentRepository studentRepository;


    public StudentController(
            StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }


    // =========================================
    // STUDENT REGISTRATION
    // =========================================

    @PostMapping
    public ResponseEntity<?> registerStudent(
            @RequestBody StudentRequest request) {

        try {

            if (request.getName() == null ||
                request.getName().trim().isEmpty()) {

                return ResponseEntity
                        .badRequest()
                        .body("Name is required");
            }


            if (request.getRegisterNumber() == null ||
                request.getRegisterNumber().trim().isEmpty()) {

                return ResponseEntity
                        .badRequest()
                        .body("Register number is required");
            }


            if (request.getEmail() == null ||
                request.getEmail().trim().isEmpty()) {

                return ResponseEntity
                        .badRequest()
                        .body("Email is required");
            }


            if (request.getPassword() == null ||
                request.getPassword().trim().isEmpty()) {

                return ResponseEntity
                        .badRequest()
                        .body("Password is required");
            }


            if (request.getPassword().length() < 6) {

                return ResponseEntity
                        .badRequest()
                        .body(
                            "Password must contain at least 6 characters"
                        );
            }


            if (studentRepository
                    .existsByEmail(request.getEmail())) {

                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Email already registered");
            }


            if (studentRepository
                    .existsByRegisterNumber(
                            request.getRegisterNumber())) {

                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Register number already registered");
            }


            Student student = new Student();

            student.setName(
                    request.getName().trim());

            student.setRegisterNumber(
                    request.getRegisterNumber().trim());

            student.setEmail(
                    request.getEmail().trim());

            student.setPassword(
                    request.getPassword());


            Student savedStudent =
                    studentRepository.save(student);


            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedStudent);

        }
        catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                        "Registration failed: "
                        + e.getMessage()
                    );
        }
    }


    // =========================================
    // STUDENT LOGIN
    // =========================================

    @PostMapping("/login")
    public ResponseEntity<?> loginStudent(
            @RequestBody StudentRequest request) {

        try {

            if (request.getEmail() == null ||
                request.getPassword() == null) {

                return ResponseEntity
                        .badRequest()
                        .body(
                            "Email and password are required"
                        );
            }


            Student student =
                    studentRepository
                            .findByEmail(
                                request.getEmail().trim()
                            )
                            .orElse(null);


            if (student == null) {

                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(
                            "Invalid email or password"
                        );
            }


            if (!student.getPassword()
                    .equals(request.getPassword())) {

                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(
                            "Invalid email or password"
                        );
            }


            return ResponseEntity.ok(student);

        }
        catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                        "Login failed: "
                        + e.getMessage()
                    );
        }
    }


    // =========================================
    // GET ALL STUDENTS
    // =========================================

    @GetMapping
    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }


    // =========================================
    // GET STUDENT BY ID
    // =========================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(
            @PathVariable Long id) {

        return studentRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(
                    ResponseEntity
                        .notFound()
                        .build()
                );
    }
}

