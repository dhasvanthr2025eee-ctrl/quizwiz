
package com.example.quizwiz.controller;

import com.example.quizwiz.entity.Question;
import com.example.quizwiz.entity.Quiz;
import com.example.quizwiz.repository.QuestionRepository;
import com.example.quizwiz.repository.QuizRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;


    public QuestionController(
            QuestionRepository questionRepository,
            QuizRepository quizRepository) {

        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
    }


    // =========================================
    // GET ALL QUESTIONS
    // =========================================

    @GetMapping
    public List<Question> getAllQuestions() {

        return questionRepository.findAll();
    }


    // =========================================
    // GET QUESTIONS BY QUIZ
    // =========================================

    @GetMapping("/quiz/{quizId}")
    public List<Question> getQuestionsByQuiz(
            @PathVariable Long quizId) {

        return questionRepository
                .findByQuizId(quizId);
    }


    // =========================================
    // GET ONE QUESTION
    // =========================================

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestion(
            @PathVariable Long id) {

        return questionRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // =========================================
    // CREATE QUESTION
    // =========================================

    @PostMapping("/quiz/{quizId}")
    public ResponseEntity<Question> createQuestion(
            @PathVariable Long quizId,
            @RequestBody Question question) {

        Quiz quiz =
                quizRepository
                        .findById(quizId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Quiz not found"
                                ));


        question.setId(null);

        question.setQuiz(quiz);


        // IMPORTANT:
        // Use correctOption everywhere

        if (question.getCorrectOption() == null ||
                question.getCorrectOption()
                        .trim()
                        .isEmpty()) {

            return ResponseEntity.badRequest()
                    .build();
        }


        Question saved =
                questionRepository.save(question);


        return ResponseEntity.ok(saved);
    }


    // =========================================
    // UPDATE QUESTION
    // =========================================

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(
            @PathVariable Long id,
            @RequestBody Question updatedQuestion) {

        return questionRepository
                .findById(id)
                .map(question -> {

                    question.setQuestionText(
                            updatedQuestion
                                    .getQuestionText()
                    );

                    question.setOptionA(
                            updatedQuestion
                                    .getOptionA()
                    );

                    question.setOptionB(
                            updatedQuestion
                                    .getOptionB()
                    );

                    question.setOptionC(
                            updatedQuestion
                                    .getOptionC()
                    );

                    question.setOptionD(
                            updatedQuestion
                                    .getOptionD()
                    );

                    question.setCorrectOption(
                            updatedQuestion
                                    .getCorrectOption()
                    );


                    return ResponseEntity.ok(
                            questionRepository.save(question)
                    );

                })
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }


    // =========================================
    // DELETE QUESTION
    // =========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long id) {

        if (!questionRepository.existsById(id)) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        questionRepository.deleteById(id);


        return ResponseEntity.noContent().build();
    }
}

