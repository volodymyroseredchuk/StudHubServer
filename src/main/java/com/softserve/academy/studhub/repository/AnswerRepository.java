package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByQuestionIdOrderByCreationDateDesc(Integer questionId);

    Integer countByUserUsername(String username);

    Integer countByApprovedTrueAndUserUsername(String username);

    @Query("select sum(a.rate) from Answer a where a.user.username = :username")
    Integer sumOfRatingByUserUsername(@Param("username") String username);
}
