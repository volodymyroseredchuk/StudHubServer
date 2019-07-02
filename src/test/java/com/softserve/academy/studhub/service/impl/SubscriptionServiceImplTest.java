package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Channel;
import com.softserve.academy.studhub.entity.Subscription;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.repository.SubscriptionRepository;
import com.softserve.academy.studhub.service.EmailService;
import com.softserve.academy.studhub.service.SocketService;
import com.softserve.academy.studhub.service.SubscriptionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private SocketService socketService;
    @Mock
    private EmailService emailService;

    @InjectMocks
    SubscriptionService subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, questionRepository, socketService, emailService);

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

    // TODO: FIX
    @Test
    public void subscriptionExistsPositive() {
        /*Subscription subscription = new Subscription();
        subscription.setChannel(new Channel());
        subscription.setUser(new User());
        subscription.setId(1);
        when(subscriptionRepository.findSubscriptionByChannelIdAndUserId(1, 1)).thenReturn(subscription);
        Assert.assertTrue(subscriptionService.subscriptionExists(1, 1));*/
    }

    @Test(expected = IllegalArgumentException.class)
    public void subscriptionExistsNull() {
        subscriptionService.subscriptionExists(1, null);
        subscriptionService.subscriptionExists(null, 1);
        subscriptionService.subscriptionExists(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void handleMessageNull() {
        subscriptionService.handleMessage(null);
    }
}