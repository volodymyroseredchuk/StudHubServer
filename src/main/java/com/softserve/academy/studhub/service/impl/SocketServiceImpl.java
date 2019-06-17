package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.service.SocketService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SocketServiceImpl implements SocketService {

    private static List<WebSocketSession> SESSION_LIST = new ArrayList<>();

    @Override
    public void addSession(WebSocketSession session) {
        SESSION_LIST.add(session);
    }

    @Override
    public void sendNotification(WebSocketSession session, TextMessage message) {
        for (WebSocketSession s : SESSION_LIST) {
            if (s != session) {
                try {
                    s.sendMessage(new TextMessage(message.getPayload()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void sendGreetings(WebSocketSession session, TextMessage message) {
        try {
            session.sendMessage(new TextMessage(message.getPayload()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeSession(WebSocketSession session) {
        SESSION_LIST.remove(session);
    }

}
