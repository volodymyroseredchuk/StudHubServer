package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.SocketChatMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.EncodeException;
import java.io.IOException;

public interface SocketService {

    void addSession(Integer id, WebSocketSession session);
    void sendNotification(Integer userId, TextMessage message);
    void sendGreetings(WebSocketSession session, Integer textId);
    void sendCustomMessage(WebSocketSession session, String msg);
    void sendChatMessage(SocketChatMessage message);
    void removeSession(WebSocketSession session);
    void removeSession(Integer id);

}
