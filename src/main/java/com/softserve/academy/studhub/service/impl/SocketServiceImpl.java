package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.coders.SocketChatMessageEncoder;
import com.softserve.academy.studhub.coders.SocketMessageEncoder;
import com.softserve.academy.studhub.entity.ChatMessage;
import com.softserve.academy.studhub.entity.SocketMessage;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.enums.SocketMessageType;
import com.softserve.academy.studhub.service.SocketService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SocketServiceImpl implements SocketService {

    private static Map<Integer, WebSocketSession> sessionIdMap = new ConcurrentHashMap<>();
    private SocketMessageEncoder messageEncoder = new SocketMessageEncoder();

    private static final SocketMessage CONNECTED_MESSAGE = new SocketMessage("Connected successfully.", SocketMessageType.NOTIFICATION);
    private static final SocketMessage NOT_CONNECTED_MESSAGE = new SocketMessage("Connected unsuccessfully. Access denied.", SocketMessageType.NOTIFICATION);
    private static final SocketMessage ERROR_MESSAGE = new SocketMessage("Error occurred.", SocketMessageType.NOTIFICATION);

    @Override
    public void addSession(Integer id, WebSocketSession session) {
        if (id != null && session != null) {
            sessionIdMap.put(id, session);
        } else {
            throw new IllegalArgumentException("Cannot add session with empty parameters.");
        }
    }

    @Override
    public void sendNotification(Integer userId, TextMessage message) {
        if (userId != null && message != null) {
            if (sessionIdMap.containsKey(userId)) {
                try {
                    sessionIdMap.get(userId).sendMessage(new TextMessage(message.getPayload()));
                } catch (IOException e) {
                    throw new IllegalArgumentException("Could not send message.");
                }
            }
        } else {
            throw new IllegalArgumentException("Cannot send notification with empty parameters.");
        }
    }

    @Override
    public void sendGreetings(WebSocketSession session, Integer textId) {

        if (session == null || textId == null) {
            throw new IllegalArgumentException("Cannot send greetings with empty parameters.");
        }
        try {
            if (textId.equals(1)) {
                session.sendMessage(new TextMessage(messageEncoder.encode(CONNECTED_MESSAGE)));
            } else if (textId.equals(2)) {
                session.sendMessage(new TextMessage(messageEncoder.encode(NOT_CONNECTED_MESSAGE)));
            } else {
                session.sendMessage(new TextMessage(messageEncoder.encode(ERROR_MESSAGE)));
            }
        } catch (EncodeException | IOException e) {
            throw new IllegalArgumentException("Could not send greetings.");
        }

    }

    @Override
    public void sendChatMessage(List<Integer> receiverIds, ChatMessage message) {

        if (message == null) {
            throw new IllegalArgumentException("Cannot send empty chat message.");
        }
        for (Integer id : receiverIds) {
            WebSocketSession session = sessionIdMap.get(id);

            if (session != null) {
                try {
                    SocketMessage socketMessage = new SocketMessage(message.getChat().getId().toString(), message.getContent(), SocketMessageType.CHAT_MESSAGE);
                    session.sendMessage(new TextMessage(messageEncoder.encode(socketMessage)));
                } catch (EncodeException | IOException e) {
                    throw new IllegalArgumentException("Could not send chat message.");
                }
            }
        }

    }

    @Override
    public void removeSession(WebSocketSession session) {
        if (session != null) {
            sessionIdMap.values().remove(session);
            try {
                session.close();
            } catch (IOException e) {
                throw new IllegalArgumentException("Could not close session.");
            }
        } else {
            throw new IllegalArgumentException("Cannot remove an empty session.");
        }
    }

    @Override
    public void removeSession(Integer id) {
        if (id != null) {
            try {
                WebSocketSession session = sessionIdMap.get(id);
                if (session != null) {
                    session.close();
                    sessionIdMap.remove(id);
                }
            } catch (IOException e) {
                throw new IllegalArgumentException("Could not close session.");
            }
        } else {
            throw new IllegalArgumentException("Cannot remove an empty ID.");
        }
    }

    @Override
    public void sendCustomMessage(WebSocketSession session, String msg){
        if (session != null && msg != null) {
            SocketMessage message = new SocketMessage(msg, SocketMessageType.NOTIFICATION);
            try {
                session.sendMessage(new TextMessage(messageEncoder.encode(message)));
            } catch (IOException | EncodeException e) {
                throw new IllegalArgumentException("Could not send custom message.");
            }
        } else {
            throw new IllegalArgumentException("Cannot send custom message with empty parameters.");
        }
    }

}
