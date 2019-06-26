package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface IQuestionService {

    Question save(Question question, Principal principal);

    Question update(Integer questionId, Question question);

    List<Question> findAll();

    Question findById(Integer questionId);

    String deleteById(Integer questionId);

    Page<Question> sortByAge(Pageable pageable);

    Page<Question> sortByTags(String[] tags, Pageable pageable);

    Page<Question> search(String[] keywords, Pageable pageable);
}
