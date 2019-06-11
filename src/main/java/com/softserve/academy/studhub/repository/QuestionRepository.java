package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findAllByOrderByCreationDateAsc();
    List<Question> findByTagListInOrderByCreationDateAsc(List<Tag> chosenTags);
}
