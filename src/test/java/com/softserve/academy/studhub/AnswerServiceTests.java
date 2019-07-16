package com.softserve.academy.studhub;

import com.softserve.academy.studhub.dto.AnswerApproveDTO;
import com.softserve.academy.studhub.dto.AnswerCreateDTO;
import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.SocketMessage;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.enums.SocketMessageType;
import com.softserve.academy.studhub.repository.AnswerRepository;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.repository.UserRepository;
import com.softserve.academy.studhub.service.AnswerService;
import com.softserve.academy.studhub.service.SubscriptionService;
import com.softserve.academy.studhub.service.impl.AnswerServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnswerServiceTests {

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private SubscriptionService subscriptionService;

    private AnswerService answerService;

    @Before
    public void initAnswerService(){
        answerService = new AnswerServiceImpl(answerRepository, questionRepository,
                userRepository, subscriptionService);
    }

    @Test
    public void createAnswerPositive() {

        AnswerCreateDTO answerCreateDTO = new AnswerCreateDTO();
        answerCreateDTO.setBody("Test data");

        User user = new User();
        user.setId(1);
        user.setUsername("testUsername");
        Mockito.when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));

        Question question = new Question();
        question.setId(1);
        Mockito.when(questionRepository.findById(1)).thenReturn(Optional.of(question));

        Answer answerResponse = new Answer(
                1,
                "Test body",
                false,
                question,
                null,
                user,
                LocalDateTime.now(),
                0
        );
        Mockito.when(answerRepository.saveAndFlush(Mockito.any())).thenReturn(answerResponse);


        SocketMessage socketMessage = new SocketMessage(question.getId().toString(), SocketMessageType.NOTIFICATION);



        Answer returnedAnswer = answerService.save(answerCreateDTO, 1, "testUsername");
        Assert.assertEquals(returnedAnswer, answerResponse);
        Mockito.verify(subscriptionService).handleMessage(socketMessage);

    }

    @Test(expected = IllegalArgumentException.class)
    public void createAnswerUnknownUser() {
        AnswerCreateDTO answerCreateDTO = new AnswerCreateDTO();
        answerCreateDTO.setBody("Test data");

        Mockito.when(userRepository.findByUsername("unknownUsername")).thenReturn(Optional.empty());

        Question question = new Question();
        question.setId(1);
        Mockito.when(questionRepository.findById(1)).thenReturn(Optional.of(question));

        Answer answerResponse = new Answer(
                1,
                "Test body",
                false,
                question,
                null,
                null,
                LocalDateTime.now(),
                0
        );
        Mockito.when(answerRepository.saveAndFlush(Mockito.any())).thenReturn(answerResponse);

        answerService.save(answerCreateDTO, 1, "unknownUsername");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createAnswerUnknownQuestion() {
        AnswerCreateDTO answerCreateDTO = new AnswerCreateDTO();
        answerCreateDTO.setBody("Test data");

        User user = new User();
        user.setId(1);
        user.setUsername("testUsername");
        Mockito.when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));

        Mockito.when(questionRepository.findById(1)).thenReturn(Optional.empty());

        Answer answerResponse = new Answer(
                1,
                "Test body",
                false,
                null,
                null,
                user,
                LocalDateTime.now(),
                0
        );
        Mockito.when(answerRepository.saveAndFlush(Mockito.any())).thenReturn(answerResponse);

        answerService.save(answerCreateDTO, 1, "testUsername");
    }

    @Test
    public void deleteAnswerPositive() {
        Answer answerToDelete = new Answer();
        answerToDelete.setApproved(false);
        Mockito.when(answerRepository.findById(1)).thenReturn(Optional.of(answerToDelete));

        Assert.assertEquals(true, answerService.deleteById(1));
        Mockito.verify(answerRepository).deleteById(1);
    }

    @Test
    public void deleteAnswerPositiveShouldNotDeleteApproved() {
        Answer answerToDelete = new Answer();
        answerToDelete.setApproved(true);
        Mockito.when(answerRepository.findById(1)).thenReturn(Optional.of(answerToDelete));

        Assert.assertEquals(false, answerService.deleteById(1));
        Mockito.verify(answerRepository, Mockito.never()).deleteById(1);
    }

    @Test
    public void deleteAnswerPositiveShouldNotDeleteUnexistingAnswer() {

        Mockito.when(answerRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assert.assertEquals(false, answerService.deleteById(1));
        Mockito.verify(answerRepository, Mockito.never()).deleteById(1);
    }

    @Test
    public void approveAnswerPositive() {
        AnswerApproveDTO answerApproveDTO = new AnswerApproveDTO();
        answerApproveDTO.setAnswerId(1);
        answerApproveDTO.setApproved(true);

        Answer answerToApprove = new Answer();
        answerToApprove.setApproved(false);
        Mockito.when(answerRepository.findById(1)).thenReturn(Optional.of(answerToApprove));
        Answer answerSaved = new Answer();
        answerSaved.setApproved(true);
        Mockito.when(answerRepository.saveAndFlush(Mockito.any(Answer.class))).thenReturn(answerSaved);

        Assert.assertEquals(answerApproveDTO, answerService.approve(answerApproveDTO));
        Mockito.verify(answerRepository).saveAndFlush(Mockito.any(Answer.class));

    }

    @Test(expected = IllegalArgumentException.class)
    public void approveAnswerShouldThrowIllegalArgumentException() {
        AnswerApproveDTO answerApproveDTO = new AnswerApproveDTO();
        answerApproveDTO.setAnswerId(1);
        answerApproveDTO.setApproved(true);


        Mockito.when(answerRepository.findById(1)).thenReturn(Optional.empty());

        Answer answerSaved = new Answer();
        answerSaved.setApproved(true);
        Mockito.when(answerRepository.saveAndFlush(Mockito.any(Answer.class))).thenReturn(answerSaved);

        Assert.assertEquals(answerApproveDTO, answerService.approve(answerApproveDTO));

    }

    @Test
    public void findByIdPositive() {
        Answer answer = new Answer();
        answer.setId(1);
        Mockito.when(answerRepository.findById(1)).thenReturn(Optional.of(answer));
        Assert.assertEquals(answerService.findById(1), answer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByIdShouldThrowIllegalArgumentException() {
        Mockito.when(answerRepository.findById(1)).thenReturn(Optional.empty());
        answerService.findById(1);
    }

    @Test
    public void findByQuestionIdPositive() {
        List<Answer> answerList = new ArrayList<>();
        Mockito.when(answerRepository.findByQuestionIdOrderByCreationDateDesc(1)).thenReturn(answerList);
        Assert.assertEquals(answerService.findAllByQuestionId(1), answerList);
    }
}