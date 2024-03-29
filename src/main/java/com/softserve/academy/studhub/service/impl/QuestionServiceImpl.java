package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.SuccessMessage;
import com.softserve.academy.studhub.entity.*;
import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class QuestionServiceImpl implements IQuestionService {

    private QuestionRepository repository;

    private TagService tagService;
    private SubscriptionService subscriptionService;
    private ChannelService channelService;
    private UserService userService;

    @Override
    public Question save(Question question, Principal principal) {
        question.setCreationDate(LocalDateTime.now());
        User user = userService.findByUsername(principal.getName());
        question.setUser(user);
        question.setTagList(tagService.reviewTagList(question.getTagList()));
        Question resultQuestion = repository.saveAndFlush(question);

        makeSubscription(resultQuestion, user);
        return resultQuestion;

    }

    private void makeSubscription(Question question, User user) {
        Channel channel = new Channel();
        channel.setQuestion(question);
        channel = channelService.save(channel);
        Subscription subscription = new Subscription();
        subscription.setChannel(channel);
        subscription.setUser(user);
        subscriptionService.save(subscription);
    }


    @Override
    public Question update(Integer questionId, Question question) {
        Question updatable = findById(questionId);
        updatable.setTitle(question.getTitle());
        updatable.setBody(question.getBody());
        updatable.setModifiedDate(LocalDateTime.now());
        updatable.setTagList(tagService.reviewTagList(question.getTagList()));

        return repository.saveAndFlush(updatable);
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
            return SuccessMessage.QUESTION_DELETED_SUCCESSFULLY;
        }
        return ErrorMessage.QUESTION_NOT_DELETED;
    }


    @Override
    public Page<Question> findAllSortedByAge(Pageable pageable) {

        return repository.findAllByTeamIsNullOrderByCreationDateDesc(pageable);

    }

    @Override
    public Page<Question> searchByTags(String[] tags, Pageable pageable) {

        return repository.findAllDistinctByTeamIsNullAndTagListInOrderByCreationDateDesc(tagService.reviewTagList(tags), pageable);
    }

    @Override
    public Page<Question> searchByKeywords(String[] keywords, Pageable pageable) {

        return repository.findByFullTextSearch(String.join(" ", keywords), pageable);
    }

    @Override
    public List<Question> findQuestionByUserUsernameOrderByCreationDateDesc(String username){

        return repository.findQuestionByUserUsernameOrderByCreationDateDesc(username);
    }

    @Override
    public Page<Question> findAllByTeamId(Integer teamId, Pageable pageable) {
        return repository.findAllByTeamIdOrderByCreationDateDesc(teamId, pageable);
    }

}
