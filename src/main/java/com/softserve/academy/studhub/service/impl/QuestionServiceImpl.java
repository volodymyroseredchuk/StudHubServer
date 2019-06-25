package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.*;
import com.softserve.academy.studhub.exceptions.ErrorMessage;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class QuestionServiceImpl implements IQuestionService {

    private QuestionRepository repository;

    private TagService tagService;
    private SubscriptionService subscriptionService;
    private ChannelService channelService;
    private UserService userService;

    public QuestionServiceImpl(QuestionRepository repository, TagService tagService, UserService userService) {
        this.repository = repository;
        this.tagService = tagService;
        this.userService = userService;
    }

    @Override
    public Question save(Question question, Principal principal) {
        question.setCreationDate(LocalDateTime.now());
        User user = userService.findByUsername(principal.getName());
        question.setUser(user);
        question.setTagList(tagService.reviewTagList(question.getTagList()));

        Question subscrQuestion  = repository.saveAndFlush(question);
        Channel channel = new Channel();
        channel.setQuestion(subscrQuestion);
        channel = channelService.save(channel);

        Subscription subscription = new Subscription();
        subscription.setChannel(channel);
        subscription.setUser(user);

        System.out.println("Channel: " + subscription.getChannel().getId() + ", User: " + subscription.getUser().getId());
        subscriptionService.save(subscription);
        return subscrQuestion;

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

    public Page<Question> sortByAge(Pageable pageable) {
        return repository.findAllByOrderByCreationDateDesc(pageable);

    }

    @Override
    public Page<Question> sortByTags(String[] tags, Pageable pageable) {
        return repository.findAllDistinctByTagListInOrderByCreationDateDesc(tagService.reviewTagList(tags), pageable);
    }

    @Override
    public Page<Question> search(String[] keywords, Pageable pageable) {
        return repository.findByFullTextSearch(String.join(" ", keywords), pageable);
    }
}
