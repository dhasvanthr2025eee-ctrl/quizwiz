
package com.example.quizwiz.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quizzes")
public class Quiz {

    // =========================================
    // ID
    // =========================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // =========================================
    // QUIZ TITLE
    // =========================================

    private String title;


    // =========================================
    // DESCRIPTION
    // =========================================

    private String description;


    // =========================================
    // TIME LIMIT
    // =========================================

    private Integer timeLimit;


    // =========================================
    // QUESTIONS
    // =========================================

    @OneToMany(
            mappedBy = "quiz",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Question> questions =
            new ArrayList<>();


    // =========================================
    // DEFAULT CONSTRUCTOR
    // =========================================

    public Quiz() {
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
    // GET TITLE
    // =========================================

    public String getTitle() {
        return title;
    }


    // =========================================
    // SET TITLE
    // =========================================

    public void setTitle(String title) {
        this.title = title;
    }


    // =========================================
    // GET DESCRIPTION
    // =========================================

    public String getDescription() {
        return description;
    }


    // =========================================
    // SET DESCRIPTION
    // =========================================

    public void setDescription(String description) {
        this.description = description;
    }


    // =========================================
    // GET TIME LIMIT
    // =========================================

    public Integer getTimeLimit() {
        return timeLimit;
    }


    // =========================================
    // SET TIME LIMIT
    // =========================================

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }


    // =========================================
    // GET QUESTIONS
    // =========================================

    public List<Question> getQuestions() {
        return questions;
    }


    // =========================================
    // SET QUESTIONS
    // =========================================

    public void setQuestions(
            List<Question> questions) {

        this.questions = questions;
    }


    // =========================================
    // ADD QUESTION
    // =========================================

    public void addQuestion(Question question) {

        questions.add(question);

        question.setQuiz(this);
    }


    // =========================================
    // REMOVE QUESTION
    // =========================================

    public void removeQuestion(
            Question question) {

        questions.remove(question);

        question.setQuiz(null);
    }
}

