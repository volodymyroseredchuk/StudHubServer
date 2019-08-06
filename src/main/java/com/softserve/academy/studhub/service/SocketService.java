package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.ChatMessage;
import com.softserve.academy.studhub.entity.SocketMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.List;

public interface SocketService {

    void addSession(Integer id, WebSocketSession session);
    void sendNotification(Integer userId, TextMessage message);
    void sendGreetings(WebSocketSession session, Integer textId);
    void sendCustomMessage(WebSocketSession session, String msg);
    void sendChatMessage(List<Integer> receiverIds, ChatMessage message);
    void sendStatus(WebSocketSession session, boolean status);
    boolean handleSocketMessage(WebSocketSession session, SocketMessage socketMessage) throws EncodeException, IOException;
    void removeSession(WebSocketSession session);
    void removeSession(Integer id);

}
