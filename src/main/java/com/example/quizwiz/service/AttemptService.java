
package com.example.quizwiz.service;

import com.example.quizwiz.dto.AnswerRequest;
import com.example.quizwiz.dto.AttemptRequest;
import com.example.quizwiz.entity.Attempt;
import com.example.quizwiz.entity.Question;
import com.example.quizwiz.entity.Quiz;
import com.example.quizwiz.entity.Student;
import com.example.quizwiz.repository.AttemptRepository;
import com.example.quizwiz.repository.QuestionRepository;
import com.example.quizwiz.repository.QuizRepository;
import com.example.quizwiz.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttemptService {

    private final AttemptRepository attemptRepository;
    private final StudentRepository studentRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public AttemptService(
            AttemptRepository attemptRepository,
            StudentRepository studentRepository,
            QuizRepository quizRepository,
            QuestionRepository questionRepository) {

        this.attemptRepository = attemptRepository;
        this.studentRepository = studentRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }


    // START QUIZ
    public Attempt startQuiz(AttemptRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with ID: "
                                        + request.getStudentId()));

        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Quiz not found with ID: "
                                        + request.getQuizId()));

        var existingAttempt =
                attemptRepository.findByStudentIdAndQuizId(
                        student.getId(),
                        quiz.getId());

        if (existingAttempt.isPresent()) {

            Attempt attempt = existingAttempt.get();

            if (Boolean.TRUE.equals(attempt.getSubmitted())) {
                throw new RuntimeException(
                        "You have already completed this quiz.");
            }

            return attempt;
        }

        Attempt attempt = new Attempt();

        attempt.setStudent(student);
        attempt.setQuiz(quiz);
        attempt.setStartTime(LocalDateTime.now());
        attempt.setScore(0);
        attempt.setSubmitted(false);

        return attemptRepository.save(attempt);
    }


    // SUBMIT QUIZ
    @Transactional
    public Attempt submitQuiz(
            Long studentId,
            Long quizId,
            List<AnswerRequest> answers) {

        Attempt attempt =
                attemptRepository
                        .findByStudentIdAndQuizId(
                                studentId,
                                quizId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Quiz attempt not found. Please start the quiz first."));

        if (Boolean.TRUE.equals(attempt.getSubmitted())) {
            throw new RuntimeException(
                    "This quiz has already been submitted.");
        }

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Quiz not found with ID: " + quizId));

        List<Question> questions =
                questionRepository.findByQuizId(quizId);

        int score = 0;

        if (answers != null) {

            for (AnswerRequest answerRequest : answers) {

                if (answerRequest == null) {
                    continue;
                }

                Long questionId =
                        answerRequest.getQuestionId();

                String selectedAnswer =
                        answerRequest.getAnswer();

                if (questionId == null
                        || selectedAnswer == null
                        || selectedAnswer.trim().isEmpty()) {
                    continue;
                }

                for (Question question : questions) {

                    if (question.getId().equals(questionId)) {

                        if (question.getCorrectOption() != null
                                && question.getCorrectOption()
                                .equalsIgnoreCase(
                                        selectedAnswer.trim())) {

                            score++;
                        }

                        break;
                    }
                }
            }
        }

        attempt.setScore(score);
        attempt.setSubmitted(true);
        attempt.setSubmissionTime(LocalDateTime.now());

        return attemptRepository.save(attempt);
    }


    // STUDENT HISTORY
    public List<Attempt> getStudentHistory(Long studentId) {

        return attemptRepository.findByStudentId(studentId);
    }


    // GET SINGLE ATTEMPT
    public Attempt getAttempt(Long id) {

        return attemptRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Attempt not found with ID: " + id));
    }


    // GET ALL ATTEMPTS
    public List<Attempt> getAllAttempts() {

        return attemptRepository.findAll();
    }


    // FACULTY REPORT
    public List<Map<String, Object>> getReport() {

        List<Attempt> attempts =
                attemptRepository.findAll();

        List<Map<String, Object>> report =
                new ArrayList<>();

        for (Attempt attempt : attempts) {

            Map<String, Object> row =
                    new HashMap<>();

            row.put(
                    "studentId",
                    attempt.getStudent().getId());

            row.put(
                    "studentName",
                    attempt.getStudent().getName());

            row.put(
                    "quizId",
                    attempt.getQuiz().getId());

            row.put(
                    "quizTitle",
                    attempt.getQuiz().getTitle());

            row.put(
                    "score",
                    attempt.getScore());

            int totalQuestions =
                    questionRepository
                            .findByQuizId(
                                    attempt.getQuiz().getId())
                            .size();

            row.put(
                    "totalQuestions",
                    totalQuestions);

            row.put(
                    "submitted",
                    attempt.getSubmitted());

            row.put(
                    "submissionTime",
                    attempt.getSubmissionTime());

            report.add(row);
        }

        return report;
    }
}

