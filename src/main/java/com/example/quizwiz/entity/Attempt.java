
package com.example.quizwiz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attempts")
public class Attempt {

    // =========================================
    // ID
    // =========================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // =========================================
    // STUDENT
    // =========================================

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnoreProperties({
            "password",
            "attempts"
    })
    private Student student;


    // =========================================
    // QUIZ
    // =========================================

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonIgnoreProperties({
            "questions"
    })
    private Quiz quiz;


    // =========================================
    // START TIME
    // =========================================

    @Column(nullable = false)
    private LocalDateTime startTime;


    // =========================================
    // SUBMISSION TIME
    // =========================================

    private LocalDateTime submissionTime;


    // =========================================
    // SCORE
    // =========================================

    @Column(nullable = false)
    private Integer score = 0;


    // =========================================
    // SUBMITTED
    // =========================================

    @Column(nullable = false)
    private Boolean submitted = false;


    // =========================================
    // DEFAULT CONSTRUCTOR
    // =========================================

    public Attempt() {
    }


    // =========================================
    // GET ID
    // =========================================

    public Long getId() {
        return id;
    }


    // =========================================
    // SET ID
    // =========================================

    public void setId(Long id) {
        this.id = id;
    }


    // =========================================
    // GET STUDENT
    // =========================================

    public Student getStudent() {
        return student;
    }


    // =========================================
    // SET STUDENT
    // =========================================

    public void setStudent(Student student) {
        this.student = student;
    }


    // =========================================
    // GET QUIZ
    // =========================================

    public Quiz getQuiz() {
        return quiz;
    }


    // =========================================
    // SET QUIZ
    // =========================================

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }


    // =========================================
    // GET START TIME
    // =========================================

    public LocalDateTime getStartTime() {
        return startTime;
    }


    // =========================================
    // SET START TIME
    // =========================================

    public void setStartTime(
            LocalDateTime startTime) {

        this.startTime = startTime;
    }


    // =========================================
    // GET SUBMISSION TIME
    // =========================================

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }


    // =========================================
    // SET SUBMISSION TIME
    // =========================================

    public void setSubmissionTime(
            LocalDateTime submissionTime) {

        this.submissionTime = submissionTime;
    }


    // =========================================
    // GET SCORE
    // =========================================

    public Integer getScore() {
        return score;
    }


    // =========================================
    // SET SCORE
    // =========================================

    public void setScore(Integer score) {
        this.score = score;
    }


    // =========================================
    // GET SUBMITTED
    // =========================================

    public Boolean getSubmitted() {
        return submitted;
    }


    // =========================================
    // SET SUBMITTED
    // =========================================

    public void setSubmitted(Boolean submitted) {
        this.submitted = submitted;
    }
}

