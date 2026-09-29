package com.example.quizwiz.service;

import com.example.quizwiz.dto.QuestionRequest;
import com.example.quizwiz.entity.Question;
import com.example.quizwiz.entity.Quiz;
import com.example.quizwiz.repository.QuestionRepository;
import com.example.quizwiz.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    public QuestionService(
            QuestionRepository questionRepository,
            QuizRepository quizRepository) {

        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
    }

    // Add question to a quiz
    public Question addQuestion(QuestionRequest request) {

        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() ->
                        new RuntimeException("Quiz not found with ID: "
                                + request.getQuizId()));

        Question question = new Question();

        question.setQuestionText(request.getQuestionText());
        question.setOptionA(request.getOptionA());
        question.setOptionB(request.getOptionB());
        question.setOptionC(request.getOptionC());
        question.setOptionD(request.getOptionD());
        question.setCorrectOption(request.getCorrectOption());
        question.setQuiz(quiz);

        return questionRepository.save(question);
    }

    // Get all questions of a quiz
    public List<Question> getQuestionsByQuiz(Long quizId) {

        return questionRepository.findByQuizId(quizId);
    }

    // Get question by ID
    public Question getQuestionById(Long id) {

        return questionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Question not found with ID: " + id));
    }

    // Delete question
    public void deleteQuestion(Long id) {

        Question question = getQuestionById(id);

        questionRepository.delete(question);
    }
}