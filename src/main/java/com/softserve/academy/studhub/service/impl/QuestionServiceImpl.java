package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.service.IQuestionService;
import com.softserve.academy.studhub.service.TagService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements IQuestionService {

    private QuestionRepository repository;

    private TagService tagService;

    public QuestionServiceImpl(QuestionRepository repository, TagService tagService) {
        this.repository = repository;
        this.tagService = tagService;
    }

    @Override
    public Question save(Question question) {
        question.setCreationDate(LocalDateTime.now());

        List<Tag> dbTagsList = new ArrayList<>();
        Tag tempTag;
        for (Tag tag : question.getTagList()) {
            try {
                tempTag = tagService.findByName(tag.getName());
            } catch (IllegalArgumentException e) {
                tempTag = tagService.save(tag);
            }
            dbTagsList.add(tempTag);
        }
        question.setTagList(dbTagsList);

        return repository.saveAndFlush(question);
    }

    @Override
    public Question update(int id, Question question) {
        question.setCreationDate(LocalDateTime.now());
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
