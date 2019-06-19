package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.SocketMessage;
import com.softserve.academy.studhub.entity.Subscription;

public interface SubscriptionService {

    Subscription save(Subscription subscription) throws IllegalArgumentException;
    boolean subscriptionExists(Integer channelId, Integer userId) throws IllegalArgumentException;
    void handleMessage(SocketMessage message) throws IllegalArgumentException;

}
