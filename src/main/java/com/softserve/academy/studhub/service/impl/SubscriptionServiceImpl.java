package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.coders.SocketMessageEncoder;
import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.SocketMessage;
import com.softserve.academy.studhub.entity.Subscription;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.enums.SocketMessageType;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.repository.SubscriptionRepository;
import com.softserve.academy.studhub.service.EmailService;
import com.softserve.academy.studhub.service.SocketService;
import com.softserve.academy.studhub.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import javax.websocket.EncodeException;
import java.util.List;

@AllArgsConstructor
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private SubscriptionRepository subscriptionRepository;
    private QuestionRepository questionRepository;
    private SocketService socketService;
    private EmailService emailService;


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
            throw new IllegalArgumentException("Invalid user param2 and/or channel param2 of a subscription argument.");
        }
    }

    @Override
    public void handleMessage(SocketMessage msg) {
        if (msg != null) {
            try {

                Integer subjectId = Integer.parseInt(msg.getParam2());
                List<User> userList = subscriptionRepository.findUserByChannelQuestionId(subjectId);
                Question question = questionRepository.findById(subjectId).orElseThrow(
                        () -> new IllegalArgumentException("Question not found."));
                sendSocketNotifications(userList);
                sendEmailNotifications(userList, question);

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid message arguments.");
            }
        } else {
            throw new IllegalArgumentException("Cannot handle an empty message.");
        }

    }

    private void sendEmailNotifications(List<User> userList, Question question) {
        new Thread(()->{
            for (User user : userList) {
                try {
                    if (user.getEmailSubscription()) {
                        emailService.sendNotificationEmail(user, question);
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void sendSocketNotifications(List<User> userList) {

        SocketMessageEncoder encoder = new SocketMessageEncoder();
        SocketMessage message = new SocketMessage("You've got a new answer for your question.", SocketMessageType.NOTIFICATION);

        new Thread(()->{
            for (User user : userList) {
                try {
                    socketService.sendNotification(user.getId(), new TextMessage(encoder.encode(message)));
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
