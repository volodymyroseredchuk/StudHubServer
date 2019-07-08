package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.SocketChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatService {

    void handleChatMessage(SocketChatMessage message);
    Page<SocketChatMessage> getMessagesByReceiverAndSender(Integer receiverId, Integer senderId, Pageable pageable);

}
