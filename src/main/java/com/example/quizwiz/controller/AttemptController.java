
package com.example.quizwiz.controller;

import com.example.quizwiz.dto.AttemptRequest;
import com.example.quizwiz.dto.SubmitAttemptRequest;
import com.example.quizwiz.entity.Attempt;
import com.example.quizwiz.service.AttemptService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attempts")
@CrossOrigin(origins = "*")
public class AttemptController {

    private final AttemptService attemptService;

    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @PostMapping("/start")
    public Attempt startQuiz(@Valid @RequestBody AttemptRequest request) {
        return attemptService.startQuiz(request);
    }

    @PostMapping("/submit")
    public Attempt submitQuiz(
            @Valid @RequestBody SubmitAttemptRequest request) {

        return attemptService.submitQuiz(
                request.getStudentId(),
                request.getQuizId(),
                request.getAnswers()
        );
    }

    @GetMapping("/report")
    public List<Map<String, Object>> getReport() {
        return attemptService.getReport();
    }

    @GetMapping("/student/{studentId}/history")
    public List<Attempt> getStudentHistory(
            @PathVariable Long studentId) {

        return attemptService.getStudentHistory(studentId);
    }

    @GetMapping("/{id}")
    public Attempt getAttempt(@PathVariable Long id) {
        return attemptService.getAttempt(id);
    }

    @GetMapping
    public List<Attempt> getAllAttempts() {
        return attemptService.getAllAttempts();
    }
}

