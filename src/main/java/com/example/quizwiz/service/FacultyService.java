package com.example.quizwiz.service;

import com.example.quizwiz.dto.FacultyRequest;
import com.example.quizwiz.entity.Faculty;
import com.example.quizwiz.repository.FacultyRepository;

import org.springframework.stereotype.Service;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty register(FacultyRequest request) {

        if (facultyRepository.existsByEmail(
                request.getEmail())) {

            throw new RuntimeException(
                    "Faculty email already exists");
        }

        Faculty faculty = new Faculty();

        faculty.setName(request.getName());
        faculty.setEmail(request.getEmail());
        faculty.setPassword(request.getPassword());

        return facultyRepository.save(faculty);
    }

    public Faculty login(String email, String password) {

        Faculty faculty = facultyRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid email or password"));

        if (!faculty.getPassword().equals(password)) {

            throw new RuntimeException(
                    "Invalid email or password");
        }

        return faculty;
    }
}