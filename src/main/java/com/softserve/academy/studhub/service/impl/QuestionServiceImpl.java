package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.service.IQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
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
        question.setCreationDate(LocalDateTime.now());
        return repository.saveAndFlush(question);
    }

    @Override
    public Question update(Integer questionId, Question question) {
        Question updatable = findById(questionId);
        updatable.setTitle(question.getTitle());
        updatable.setBody(question.getBody());
        updatable.setModifiedDate(LocalDateTime.now());
        return repository.saveAndFlush(updatable);
    }

    @Override
    public List<Question> findAll() {
        return repository.findAll();
    }

    @Override
    public Question findById(Integer questionId) {
        Optional<Question> result = repository.findById(questionId);
        if (!result.isPresent()) {
            throw new IllegalArgumentException("Requested question does not exist");
        }
        return result.get();

    }

    //need to change "if" block after answer dao& service ready.
    @Override
    public void deleteById(Integer questionId) {
        Question questionToDelete = findById(questionId);
        try {
            if ((questionToDelete.getAnswerList().isEmpty()) || (questionToDelete.getAnswerList() == null)) {
                repository.deleteById(questionId);
            } else {
                throw new OperationNotSupportedException("This question already has answers and can not be deleted");
            }
        } catch (OperationNotSupportedException e) {
            e.getMessage();
        }
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
