package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.SocketChatMessage;
import com.softserve.academy.studhub.repository.ChatMessageRepository;
import com.softserve.academy.studhub.service.ChatService;
import com.softserve.academy.studhub.service.SocketService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private ChatMessageRepository chatRepository;
    private SocketService socketService;

    @Override
    public void handleChatMessage(SocketChatMessage message) {

        if (message == null) {
            throw new IllegalArgumentException("Cannot send an empty chat message.");
        }

        chatRepository.saveAndFlush(message);

        socketService.sendChatMessage(message);
    }

    @Override
    public Page<SocketChatMessage> getMessagesByReceiverAndSender(Integer receiverId, Integer senderId, Pageable pageable) {

        if (receiverId == null || senderId == null) {
            throw new IllegalArgumentException("Cannot get messages by empty ID-s.");
        }

        return chatRepository.findByReceiverIdAndSenderId(receiverId, senderId, pageable);

    }

}
