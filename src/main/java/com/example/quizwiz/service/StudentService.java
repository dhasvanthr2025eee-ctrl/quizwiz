package com.example.quizwiz.service;

import com.example.quizwiz.dto.StudentRequest;
import com.example.quizwiz.entity.Student;
import com.example.quizwiz.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(StudentRequest request) {

        if (studentRepository.existsByRegisterNumber(
                request.getRegisterNumber())) {

            throw new RuntimeException(
                    "Register number already exists");
        }

        if (studentRepository.existsByEmail(
                request.getEmail())) {

            throw new RuntimeException(
                    "Email already exists");
        }

        Student student = new Student();

        student.setName(request.getName());
        student.setRegisterNumber(request.getRegisterNumber());
        student.setEmail(request.getEmail());

        // Simple project version
        student.setPassword(request.getPassword());

        return studentRepository.save(student);
    }

    public Student login(String registerNumber, String password) {

        Student student = studentRepository
                .findByRegisterNumber(registerNumber)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid register number or password"));

        if (!student.getPassword().equals(password)) {

            throw new RuntimeException(
                    "Invalid register number or password");
        }

        return student;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found"));
    }
}