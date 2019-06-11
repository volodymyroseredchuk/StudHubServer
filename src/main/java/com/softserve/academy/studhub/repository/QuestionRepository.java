package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
