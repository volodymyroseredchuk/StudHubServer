package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.exceptions.ErrorMessage;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.service.IQuestionService;
import com.softserve.academy.studhub.service.TagService;
import com.softserve.academy.studhub.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class QuestionServiceImpl implements IQuestionService {

    private QuestionRepository repository;

    private TagService tagService;

    private UserService userService;

    public QuestionServiceImpl(QuestionRepository repository, TagService tagService, UserService userService) {
        this.repository = repository;
        this.tagService = tagService;
        this.userService = userService;
    }

    @Override
    public Question save(Question question, Principal principal) {
        question.setCreationDate(LocalDateTime.now());
        question.setUser(userService.findByUserName(principal.getName()));

        question.setTagList(tagService.reviewTagList(question.getTagList()));

        return repository.saveAndFlush(question);
    }
// TODO: Delete after ui is ready.

    @Override
    public Question saveNoUser(Question question) {
        question.setCreationDate(LocalDateTime.now());

        question.setTagList(tagService.reviewTagList(question.getTagList()));

        return repository.saveAndFlush(question);
    }

    @Override

    public Question update(Integer questionId, Question question) {
        Question updatable = findById(questionId);
        updatable.setTitle(question.getTitle());
        updatable.setBody(question.getBody());
        updatable.setModifiedDate(LocalDateTime.now());
        updatable.setTagList(tagService.reviewTagList(updatable.getTagList()));

        return repository.saveAndFlush(updatable);
    }

    @Override
    public List<Question> findAll() {
        return repository.findAll();
    }

    @Override
    public Question findById(Integer questionId) throws NotFoundException {

        return repository.findById(questionId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.QUESTION_NOTFOUND + questionId));

    }

    @Override
    public String deleteById(Integer questionId) {
        Question questionToDelete = findById(questionId);

        if ((questionToDelete.getAnswerList().isEmpty()) || (questionToDelete.getAnswerList() == null)) {
            repository.deleteById(questionId);
            return "Question deleted";
        }
        return "This question already has answers and can not be deleted";
    }

    @Override
    public List<Question> sortByAge() {
        return repository.findAllByOrderByCreationDateAsc();
    }

    @Override
    public List<Question> sortByTag(List<Tag> tags) {
        return repository.findAllByTagListInOrderByCreationDateAsc(tags);
    }

    @Override
    public Page<Question> search(String[] keywords, Pageable pageable) {
        return repository.findByFullTextSearch(String.join(" ", keywords), pageable);
    }
}
