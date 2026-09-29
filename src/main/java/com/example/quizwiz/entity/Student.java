
package com.example.quizwiz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String registerNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;


    // =========================
    // DEFAULT CONSTRUCTOR
    // =========================

    public Student() {
    }


    // =========================
    // GET ID
    // =========================

    public Long getId() {
        return id;
    }


    // =========================
    // SET ID
    // =========================

    public void setId(Long id) {
        this.id = id;
    }


    // =========================
    // GET NAME
    // =========================

    public String getName() {
        return name;
    }


    // =========================
    // SET NAME
    // =========================

    public void setName(String name) {
        this.name = name;
    }


    // =========================
    // GET REGISTER NUMBER
    // =========================

    public String getRegisterNumber() {
        return registerNumber;
    }


    // =========================
    // SET REGISTER NUMBER
    // =========================

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }


    // =========================
    // GET EMAIL
    // =========================

    public String getEmail() {
        return email;
    }


    // =========================
    // SET EMAIL
    // =========================

    public void setEmail(String email) {
        this.email = email;
    }


    // =========================
    // GET PASSWORD
    // =========================

    public String getPassword() {
        return password;
    }


    // =========================
    // SET PASSWORD
    // =========================

    public void setPassword(String password) {
        this.password = password;
    }
}
