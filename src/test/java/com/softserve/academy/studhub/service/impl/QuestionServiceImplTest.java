package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.dto.QuestionDTO;
import com.softserve.academy.studhub.entity.*;
import com.softserve.academy.studhub.entity.enums.SocketMessageType;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.service.*;
import com.sun.security.auth.UserPrincipal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private TagService tagService;
    @Mock
    private SubscriptionService subscriptionService;
    @Mock
    private ChannelService channelService;
    @Mock
    private UserService userService;

    private IQuestionService questionService;

    @Before
    public void initQuestionService() throws Exception {
        questionService = new QuestionServiceImpl(questionRepository, tagService, subscriptionService, channelService, userService);
    }

    @Test
    public void save() {
        Question question = new Question();
        question.setBody("Test data");

        User user = new User();
        user.setId(1);
        user.setUsername("testUsername");
        Mockito.when(userService.findByUsername("testUsername")).thenReturn(user);

        Question questionResponse = new Question(
                1,
                "Test title",
                "Test body",
                LocalDateTime.now(),
                LocalDateTime.now(),
                user,
                null,
                null,
                null
        );
        Mockito.when(questionRepository.saveAndFlush(Mockito.any())).thenReturn(questionResponse);
        Principal principal = new UserPrincipal(user.getUsername());
        Question returnedQuestion = questionService.save(question, principal);
        Assert.assertEquals(returnedQuestion, questionResponse);

    }

    @Test (expected = NullPointerException.class)
    public void saveWithIncorrectUser() {
        Question question = new Question();
        question.setBody("Test data");

        User user = new User();
        user.setId(1);
        user.setUsername("testUsername");
        Mockito.when(userService.findByUsername("testUsername")).thenReturn(user);

        Question questionResponse = new Question(
                1,
                "Test title",
                "Test body",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                null,
                null,
                null
        );
        Mockito.when(questionRepository.saveAndFlush(Mockito.any())).thenReturn(questionResponse);
        Principal principal = new UserPrincipal(null);
        questionService.save(question, principal);
    }

    @Test
    public void update() {
        Question questionToUpdate = new Question();
        questionToUpdate.setId(1);
        Question question = new Question();
        Mockito.when(questionRepository.findById(1)).thenReturn(Optional.of(questionToUpdate));
        assertNull(questionService.update(1, question));
        Mockito.verify(questionRepository).saveAndFlush(questionToUpdate);
    }

    @Test
    public void findById() {
        Question question = new Question();
        Mockito.when(questionRepository.findById(1)).thenReturn(Optional.of(question));
        Assert.assertEquals(questionService.findById(1), question);
    }

    @Test (expected = NotFoundException.class)
    public void findByIncorrectId() {
        Mockito.when(questionRepository.findById(1)).thenReturn(Optional.empty());
        questionService.findById(1);
    }

    @Test
    public void deleteById() {
        Question questionToDelete = new Question();
        List<Answer> answerList = new ArrayList<>();
        questionToDelete.setAnswerList(answerList);
        Mockito.when(questionRepository.findById(1)).thenReturn(Optional.of(questionToDelete));
        Assert.assertEquals("Question deleted", questionService.deleteById(1));
        Mockito.verify(questionRepository).deleteById(1);
    }

    @Test
    public void deleteByIdAnswerListNotEmpty() {
        Question questionToDelete = new Question();
        Answer answer = new Answer();
        List<Answer> answerList = new ArrayList<>();
        answerList.add(answer);
        questionToDelete.setId(1);
        questionToDelete.setAnswerList(answerList);
        Mockito.when(questionRepository.findById(1)).thenReturn(Optional.of(questionToDelete));
        Assert.assertEquals("This question already has answers and can not be deleted", questionService.deleteById(1));
        Mockito.verify(questionRepository, Mockito.never()).deleteById(1);
    }

    @Test
    public void findAllSortedByAge() {

        List<Question> questionList = new ArrayList<>();
        Page<Question> questions = new PageImpl<>(questionList);
        Mockito.when(questionRepository.findAllByTeamIsNullOrderByCreationDateDesc(PageRequest.of(0,2))).thenReturn(questions);
        Assert.assertEquals(questionService.findAllSortedByAge(PageRequest.of(0,2)), questions);
    }

    @Test
    public void searchByTags() {
        String[] tags = {"test"};
        Set<Tag> tagSet = tagService.reviewTagList(tags);
        List<Question> questionList = new ArrayList<>();
        Page<Question> questions = new PageImpl<>(questionList);
        Mockito.when(questionRepository.findAllDistinctByTeamIsNullAndTagListInOrderByCreationDateDesc(tagSet, PageRequest.of(0,2))).thenReturn(questions);
        Assert.assertEquals(questionService.searchByTags(tags, PageRequest.of(0,2)), questions);

    }

    @Test
    public void searchByKeywords() {
        String[] keywords = {"test"};
        List<Question> questionList = new ArrayList<>();
        Page<Question> questions = new PageImpl<>(questionList);
        Mockito.when(questionRepository.findByFullTextSearch(String.join(" ", keywords), PageRequest.of(0,2))).thenReturn(questions);
        Assert.assertEquals(questionService.searchByKeywords(keywords, PageRequest.of(0,2)), questions);
    }

    @Test
    public void findQuestionByUserUsernameOrderByCreationDateDesc() {
        List<Question> questionList = new ArrayList<>();
        User user = new User();
        user.setUsername("testUsername");
        Mockito.when(questionRepository.findQuestionByUserUsernameOrderByCreationDateDesc(user.getUsername())).thenReturn(questionList);
        Assert.assertEquals(questionService.findQuestionByUserUsernameOrderByCreationDateDesc(user.getUsername()), questionList);
    }

    @Test
    public void findAllByTeamId() {
        List<Question> questionList = new ArrayList<>();
        Page<Question> questions = new PageImpl<>(questionList);
        Mockito.when(questionRepository.findAllByTeamIdOrderByCreationDateDesc(1, PageRequest.of(0,2))).thenReturn(questions);
        Assert.assertEquals(questionService.findAllByTeamId(1, PageRequest.of(0,2)), questions);
    }
}