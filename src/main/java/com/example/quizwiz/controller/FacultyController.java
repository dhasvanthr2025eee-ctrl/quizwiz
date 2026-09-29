package com.example.quizwiz.controller;

import com.example.quizwiz.dto.FacultyRequest;
import com.example.quizwiz.entity.Faculty;
import com.example.quizwiz.service.FacultyService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("/register")
    public ResponseEntity<Faculty> register(
            @Valid @RequestBody FacultyRequest request) {

        return new ResponseEntity<>(
                facultyService.register(request),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Faculty> login(
            @RequestBody Map<String, String> loginData) {

        Faculty faculty = facultyService.login(
                loginData.get("email"),
                loginData.get("password")
        );

        return ResponseEntity.ok(faculty);
    }
}