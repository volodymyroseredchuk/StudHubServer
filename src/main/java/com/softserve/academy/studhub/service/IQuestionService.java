package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.Tag;

import java.util.List;

public interface IQuestionService {

    Question save(Question question);

    Question update(Question question);

    List<Question> findAll();

    Question findById(int id);

    void deleteById(int id);

    List<Question> sortByAge();

    List<Question> sortByTag(List<Tag> tags);

}
