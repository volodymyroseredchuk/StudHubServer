package com.softserve.academy.studhub.service;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.EncodeException;
import java.io.IOException;

public interface SocketService {

    void addSession(Integer id, WebSocketSession session);
    void sendNotification(Integer userId, TextMessage message) throws IOException;
    void sendGreetings(WebSocketSession session, Integer textId) throws IOException, EncodeException;
    void sendCustomMessage(WebSocketSession session, String msg) throws EncodeException, IOException;
    void removeSession(WebSocketSession session);

}
