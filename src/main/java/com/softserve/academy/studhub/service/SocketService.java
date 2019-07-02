package com.softserve.academy.studhub.service;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.EncodeException;
import java.io.IOException;

public interface SocketService {

    void addSession(Integer id, WebSocketSession session);
    void sendNotification(Integer userId, TextMessage message);
    void sendGreetings(WebSocketSession session, Integer textId);
    void sendCustomMessage(WebSocketSession session, String msg);
    void removeSession(WebSocketSession session);

}
