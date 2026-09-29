
package com.example.quizwiz.repository;

import com.example.quizwiz.entity.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {

    Optional<Attempt> findByStudentIdAndQuizId(
            Long studentId,
            Long quizId
    );

    List<Attempt> findByStudentId(
            Long studentId
    );

    List<Attempt> findByQuizId(
            Long quizId
    );

    boolean existsByStudentIdAndQuizId(
            Long studentId,
            Long quizId
    );

    void deleteByQuizId(
            Long quizId
    );
}

