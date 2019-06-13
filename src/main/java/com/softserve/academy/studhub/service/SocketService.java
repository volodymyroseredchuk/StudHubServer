package com.softserve.academy.studhub.service;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public interface SocketService {

    void addSession(WebSocketSession session);
    void sendNotification(WebSocketSession session, TextMessage message);
    void sendGreetings(WebSocketSession session, TextMessage message);
    void removeSession(WebSocketSession session);

}
