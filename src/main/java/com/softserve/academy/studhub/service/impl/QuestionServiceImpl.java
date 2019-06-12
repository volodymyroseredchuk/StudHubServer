package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.service.IQuestionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements IQuestionService {

    private QuestionRepository repository;

    public QuestionServiceImpl(QuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Question save(Question question) {
        return repository.saveAndFlush(question);
    }

    @Override
    public Question update(int id, Question question) {

        Question updatable = findById(id);
        updatable = question;
        return repository.saveAndFlush(updatable);
    }

    @Override
    public List<Question> findAll() {
        return repository.findAll();
    }

    @Override
    public Question findById(int id) {
        Optional<Question> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new IllegalArgumentException("Requested question does not exist");
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public List<Question> sortByAge() {
        return repository.findAllByOrderByCreationDateAsc();
    }

    @Override
    public List<Question> sortByTag(List<Tag> tags) {
        return repository.findAllByTagListInOrderByCreationDateAsc(tags);
    }
}
