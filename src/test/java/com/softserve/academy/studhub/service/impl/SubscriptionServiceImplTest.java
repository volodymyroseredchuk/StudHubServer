package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.*;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.repository.SubscriptionRepository;
import com.softserve.academy.studhub.service.EmailService;
import com.softserve.academy.studhub.service.SocketService;
import com.softserve.academy.studhub.service.SubscriptionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private SocketService socketService;
    @Mock
    private EmailService emailService;

    SubscriptionService subscriptionService;

    @Before
    public void initialize() {
        subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, questionRepository, socketService, emailService);
    }

    @Test
    public void savePositive() {
        Subscription subscription = new Subscription();
        subscription.setUser(new User());
        subscription.setChannel(new Channel());
        when(subscriptionRepository.saveAndFlush(subscription)).thenReturn(subscription);

        Assert.assertEquals(subscriptionService.save(subscription), subscription);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNull() {
        subscriptionService.save(null);
    }

    @Test
    public void subscriptionExistsPositive() {
        Subscription subscription = new Subscription();
        subscription.setChannel(new Channel());
        subscription.setUser(new User());
        subscription.setId(1);
        Mockito.when(subscriptionRepository.findSubscriptionByChannelIdAndUserId(1, 1)).thenReturn(Optional.of(subscription));
        Assert.assertTrue(subscriptionService.subscriptionExists(1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void subscriptionExistsNull() {
        subscriptionService.subscriptionExists(1, null);
        subscriptionService.subscriptionExists(null, 1);
        subscriptionService.subscriptionExists(null, null);
    }

    @Test
    public void handleMessagePositive() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        List<Question> questionList = new ArrayList<>();
        questionList.add(new Question());
        when(subscriptionRepository.findUserByChannelQuestionId(1)).thenReturn(userList);
        when(questionRepository.findById(1)).thenReturn(Optional.of(new Question()));
        subscriptionService.handleMessage(new SocketMessage("1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void handleMessageNull() {
        subscriptionService.handleMessage(null);
    }
}