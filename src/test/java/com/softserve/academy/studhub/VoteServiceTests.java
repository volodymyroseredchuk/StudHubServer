package com.softserve.academy.studhub;

import com.softserve.academy.studhub.dto.VotePostDTO;
import com.softserve.academy.studhub.entity.*;
import com.softserve.academy.studhub.repository.*;
import com.softserve.academy.studhub.service.VoteService;
import com.softserve.academy.studhub.service.impl.VoteServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VoteServiceTests {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private QuestionRepository questionRepository;

    private VoteService voteService;

    @Before
    public void initializeVoteService() {
        voteService = new VoteServiceImpl(voteRepository, userRepository, answerRepository,
                feedbackRepository, questionRepository);
    }

    @Test
    public void findAllByUsernameAndQuestionIdPositive() {

        Question question = new Question();
        question.setId(1);
        Mockito.when(questionRepository.findById(1)).thenReturn(Optional.of(question));

        User user = new User();
        user.setId(1);
        user.setUsername("testUsername");
        Mockito.when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));

        List<Vote> votes = new ArrayList<>();

        Mockito.when(voteRepository.findByUserAndAnswer_Question(user, question)).thenReturn(votes);

        Assert.assertEquals(voteService.findByUsernameAndQuestionId("testUsername", 1), votes);
    }

    @Test
    public void findAllByUsernameAndQuestionIdPositiveUnexistingQuestionShouldReturnEmptyList() {

        Question question = new Question();
        question.setId(1);
        Mockito.when(questionRepository.findById(1)).thenReturn(Optional.empty());

        User user = new User();
        user.setId(1);
        user.setUsername("testUsername");
        Mockito.when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));

        List<Vote> votes = new ArrayList<>();
        Mockito.when(voteRepository.findByUserAndAnswer_Question(user, question)).thenReturn(votes);

        Assert.assertEquals(voteService.findByUsernameAndQuestionId("testUsername", 1), new ArrayList<>());
    }

    @Test
    public void findAllByUsernameAndQuestionIdPositiveUnexistingUserShouldReturnEmptyList() {

        Question question = new Question();
        question.setId(1);
        Mockito.when(questionRepository.findById(1)).thenReturn(Optional.of(question));

        User user = new User();
        user.setId(1);
        user.setUsername("testUsername");
        Mockito.when(userRepository.findByUsername("testUsername")).thenReturn(Optional.empty());

        List<Vote> votes = new ArrayList<>();
        Mockito.when(voteRepository.findByUserAndAnswer_Question(user, question)).thenReturn(votes);

        Assert.assertEquals(voteService.findByUsernameAndQuestionId("testUsername", 1), new ArrayList<>());
    }

    @Test
    public void updateVoteForAnswerExsisting() {
        VotePostDTO votePostDTO = new VotePostDTO();
        votePostDTO.setAnswerId(1);
        votePostDTO.setValue(1);

        Answer answer = new Answer();
        answer.setId(1);
        answer.setRate(3);
        Mockito.when(answerRepository.findById(1)).thenReturn(Optional.of(answer));

        User user = new User();
        user.setId(1);
        user.setUsername("testUsername");
        Mockito.when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));

        Vote vote = new Vote();
        vote.setAnswer(answer);
        vote.setUser(user);
        vote.setValue(-1);
        Mockito.when(voteRepository.findByUserAndAnswer(user, answer)).thenReturn(Optional.of(vote));


        Vote resultVote = new Vote();
        resultVote.setValue(1);
        resultVote.setAnswer(answer);
        resultVote.setUser(user);
        Mockito.when(voteRepository.saveAndFlush(resultVote)).thenReturn(resultVote);

        Assert.assertEquals(voteService.update(votePostDTO, "testUsername"), resultVote);
        Answer resultAnswer = new Answer();
        resultAnswer.setId(1);
        resultAnswer.setRate(5);
        ArgumentCaptor<Answer> argument = ArgumentCaptor.forClass(Answer.class);

        Mockito.verify(answerRepository).saveAndFlush(argument.capture());
        Assert.assertEquals(argument.getValue().getRate(), resultAnswer.getRate());

    }

    @Test
    public void updateVoteForFeedbackExsisting() {
        VotePostDTO votePostDTO = new VotePostDTO();
        votePostDTO.setFeedbackId(1);
        votePostDTO.setValue(1);

        Feedback feedback = new Feedback();
        feedback.setId(1);
        feedback.setRate(3);
        Mockito.when(feedbackRepository.findById(1)).thenReturn(Optional.of(feedback));

        User user = new User();
        user.setId(1);
        user.setUsername("testUsername");
        Mockito.when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));

        Vote vote = new Vote();
        vote.setFeedback(feedback);
        vote.setUser(user);
        vote.setValue(-1);
        Mockito.when(voteRepository.findByUserAndFeedback(user, feedback)).thenReturn(Optional.of(vote));


        Vote resultVote = new Vote();
        resultVote.setValue(1);
        resultVote.setFeedback(feedback);
        resultVote.setUser(user);
        Mockito.when(voteRepository.saveAndFlush(resultVote)).thenReturn(resultVote);

        Assert.assertEquals(voteService.update(votePostDTO, "testUsername"), resultVote);

        Feedback resultFeedback = new Feedback();
        resultFeedback.setId(1);
        resultFeedback.setRate(5);
        ArgumentCaptor<Feedback> argument = ArgumentCaptor.forClass(Feedback.class);

        Mockito.verify(feedbackRepository).saveAndFlush(argument.capture());
        Assert.assertEquals(argument.getValue().getRate(), resultFeedback.getRate());

    }

    @Test
    public void updateVoteForAnswerUnexsisting() {
        VotePostDTO votePostDTO = new VotePostDTO();
        votePostDTO.setAnswerId(1);
        votePostDTO.setValue(1);

        Answer answer = new Answer();
        answer.setId(1);
        answer.setRate(3);
        Mockito.when(answerRepository.findById(1)).thenReturn(Optional.of(answer));

        User user = new User();
        user.setId(1);
        user.setUsername("testUsername");
        Mockito.when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));

        Mockito.when(voteRepository.findByUserAndAnswer(user, answer)).thenReturn(Optional.empty());

        Vote resultVote = new Vote();
        resultVote.setValue(1);
        resultVote.setAnswer(answer);
        resultVote.setUser(user);
        Mockito.when(voteRepository.saveAndFlush(resultVote)).thenReturn(resultVote);

        Assert.assertEquals(voteService.update(votePostDTO, "testUsername"), resultVote);
        Answer resultAnswer = new Answer();
        resultAnswer.setId(1);
        resultAnswer.setRate(4);
        ArgumentCaptor<Answer> argument = ArgumentCaptor.forClass(Answer.class);

        Mockito.verify(answerRepository).saveAndFlush(argument.capture());
        Assert.assertEquals(argument.getValue().getRate(), resultAnswer.getRate());

    }

    @Test
    public void updateVoteForFeedbackUnexsisting() {
        VotePostDTO votePostDTO = new VotePostDTO();
        votePostDTO.setFeedbackId(1);
        votePostDTO.setValue(1);

        Feedback feedback = new Feedback();
        feedback.setId(1);
        feedback.setRate(3);
        Mockito.when(feedbackRepository.findById(1)).thenReturn(Optional.of(feedback));

        User user = new User();
        user.setId(1);
        user.setUsername("testUsername");
        Mockito.when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));


        Mockito.when(voteRepository.findByUserAndFeedback(user, feedback)).thenReturn(Optional.empty());


        Vote resultVote = new Vote();
        resultVote.setValue(1);
        resultVote.setFeedback(feedback);
        resultVote.setUser(user);
        Mockito.when(voteRepository.saveAndFlush(resultVote)).thenReturn(resultVote);

        Assert.assertEquals(voteService.update(votePostDTO, "testUsername"), resultVote);

        Feedback resultFeedback = new Feedback();
        resultFeedback.setId(1);
        resultFeedback.setRate(4);
        ArgumentCaptor<Feedback> argument = ArgumentCaptor.forClass(Feedback.class);

        Mockito.verify(feedbackRepository).saveAndFlush(argument.capture());
        Assert.assertEquals(argument.getValue().getRate(), resultFeedback.getRate());

    }

    @Test(expected = NullPointerException.class)
    public void updateVoteShouldThrowNullReferenceException() {
        VotePostDTO votePostDTO = new VotePostDTO();
        votePostDTO.setValue(1);
        User user = new User();
        user.setId(1);
        user.setUsername("testUsername");
        Mockito.when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));

        voteService.update(votePostDTO, "testUsername");
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateVoteShouldThrowIllegalArgumentException() {
        VotePostDTO votePostDTO = new VotePostDTO();
        votePostDTO.setValue(1);
        Mockito.when(userRepository.findByUsername("testUsername")).thenReturn(Optional.empty());

        voteService.update(votePostDTO, "testUsername");
    }
}
