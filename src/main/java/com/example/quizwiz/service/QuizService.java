
package com.example.quizwiz.service;

import com.example.quizwiz.dto.QuestionRequest;
import com.example.quizwiz.dto.QuizRequest;
import com.example.quizwiz.entity.Question;
import com.example.quizwiz.entity.Quiz;
import com.example.quizwiz.repository.AttemptRepository;
import com.example.quizwiz.repository.QuestionRepository;
import com.example.quizwiz.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AttemptRepository attemptRepository;

    public QuizService(
            QuizRepository quizRepository,
            QuestionRepository questionRepository,
            AttemptRepository attemptRepository) {

        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.attemptRepository = attemptRepository;
    }

    // CREATE QUIZ WITH QUESTIONS
    public Quiz createQuiz(QuizRequest request) {

        Quiz quiz = new Quiz();

        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setTimeLimit(request.getTimeLimit());

        if (request.getQuestions() != null) {

            for (QuestionRequest qr : request.getQuestions()) {

                Question question = new Question();

                question.setQuestionText(qr.getQuestionText());
                question.setOptionA(qr.getOptionA());
                question.setOptionB(qr.getOptionB());
                question.setOptionC(qr.getOptionC());
                question.setOptionD(qr.getOptionD());
                question.setCorrectOption(qr.getCorrectOption());

                // Connect question to quiz
                quiz.addQuestion(question);
            }
        }

        return quizRepository.save(quiz);
    }

    // GET ALL QUIZZES
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    // GET QUIZ BY ID
    public Quiz getQuizById(Long id) {

        return quizRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Quiz not found with ID: " + id));
    }

    // UPDATE QUIZ
    public Quiz updateQuiz(Long id, QuizRequest request) {

        Quiz quiz = getQuizById(id);

        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setTimeLimit(request.getTimeLimit());

        return quizRepository.save(quiz);
    }

    // DELETE QUIZ
    @Transactional
    public void deleteQuiz(Long id) {

        Quiz quiz = getQuizById(id);

        // Delete attempts first
        attemptRepository.deleteByQuizId(id);

        // Delete questions
        questionRepository.deleteByQuizId(id);

        // Delete quiz
        quizRepository.delete(quiz);
    }
}

