package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.coders.SocketMessageEncoder;
import com.softserve.academy.studhub.entity.SocketMessage;
import com.softserve.academy.studhub.entity.Subscription;
import com.softserve.academy.studhub.repository.SubscriptionRepository;
import com.softserve.academy.studhub.service.SocketService;
import com.softserve.academy.studhub.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private SubscriptionRepository subscriptionRepository;
    private SocketService socketService;

    @Override
    public Subscription save(Subscription subscription) {
        if (subscription != null) {
            return subscriptionRepository.saveAndFlush(subscription);
        } else {
            throw new IllegalArgumentException("Invalid subscription argument.");
        }
    }

    @Override
    public boolean subscriptionExists(Integer channelId, Integer userId) {
        if (channelId != null && userId != null) {
            return subscriptionRepository.findSubscriptionByChannelIdAndUserId(channelId, userId).isPresent();
        } else {
            throw new IllegalArgumentException("Invalid user id and/or channel id of a subscription argument.");
        }
    }

    @Override
    public void handleMessage(SocketMessage msg) {
        try {
            Integer subjectId = Integer.parseInt(msg.getId());
            List<Integer> userId = subscriptionRepository.findUserIdByChannelQuestionId(subjectId);

            SocketMessageEncoder encoder = new SocketMessageEncoder();
            SocketMessage message = new SocketMessage("You've got a new answer for your question.");
            for (Integer id : userId) {
                socketService.sendNotification(id, new TextMessage(encoder.encode(message)));
            }

        } catch (IOException | EncodeException e) {
            throw new IllegalArgumentException("Message processing failed.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid message arguments.");
        }

    }

}
