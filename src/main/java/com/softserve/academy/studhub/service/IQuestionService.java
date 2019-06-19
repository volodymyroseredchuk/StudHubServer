package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IQuestionService {

    Question save(Question question);

    Question update(Integer questionId, Question question);

    List<Question> findAll();

    Question findById(Integer questionId);

    void deleteById(Integer questionId);

    List<Question> sortByAge();

    List<Question> sortByTag(List<Tag> tags);

    Page<Question> search(String[] keywords, Pageable pageable);
}
