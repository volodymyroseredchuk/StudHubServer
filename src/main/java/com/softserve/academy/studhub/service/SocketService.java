package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.ChatMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public interface SocketService {

    void addSession(Integer id, WebSocketSession session);
    void sendNotification(Integer userId, TextMessage message);
    void sendGreetings(WebSocketSession session, Integer textId);
    void sendCustomMessage(WebSocketSession session, String msg);
    void sendChatMessage(ChatMessage message);
    void removeSession(WebSocketSession session);
    void removeSession(Integer id);

}
