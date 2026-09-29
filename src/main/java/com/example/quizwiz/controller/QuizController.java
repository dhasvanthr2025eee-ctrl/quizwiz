
package com.example.quizwiz.controller;

import com.example.quizwiz.dto.QuizRequest;
import com.example.quizwiz.entity.Quiz;
import com.example.quizwiz.service.QuizService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "*")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // Create Quiz
    @PostMapping
    public ResponseEntity<Quiz> createQuiz(
            @Valid @RequestBody QuizRequest request) {

        Quiz quiz = quizService.createQuiz(request);

        return new ResponseEntity<>(
                quiz,
                HttpStatus.CREATED
        );
    }

    // Get all quizzes
    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {

        return ResponseEntity.ok(
                quizService.getAllQuizzes()
        );
    }

    // Get quiz by ID
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                quizService.getQuizById(id)
        );
    }

    // Update quiz
    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(
            @PathVariable Long id,
            @Valid @RequestBody QuizRequest request) {

        return ResponseEntity.ok(
                quizService.updateQuiz(id, request)
        );
    }

    // Delete quiz
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(
            @PathVariable Long id) {

        quizService.deleteQuiz(id);

        return ResponseEntity.noContent().build();
    }
}

