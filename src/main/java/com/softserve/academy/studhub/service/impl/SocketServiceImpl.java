package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.coders.SocketMessageEncoder;
import com.softserve.academy.studhub.entity.ChatMessage;
import com.softserve.academy.studhub.entity.SocketMessage;
import com.softserve.academy.studhub.entity.enums.SocketMessageType;
import com.softserve.academy.studhub.service.ChatService;
import com.softserve.academy.studhub.service.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SocketServiceImpl implements SocketService {
    @Autowired
    private ChatService chatService;

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
        if (message == null || receiverIds == null) {
            throw new IllegalArgumentException("Cannot send empty chat message or an empty receivers ID-s.");
        }

        for (Integer id : receiverIds) {
            WebSocketSession session = sessionIdMap.get(id);

            if (session != null) {
                try {
                    SocketMessage socketMessage = new SocketMessage(message.getChat().getId().toString(),
                            Boolean.toString(message.getChat().getSecret()), SocketMessageType.CHAT_MESSAGE);
                    session.sendMessage(new TextMessage(messageEncoder.encode(socketMessage)));
                } catch (EncodeException | IOException e) {
                    throw new IllegalArgumentException("Could not send chat message.");
                }
            }
        }

    }

    @Override
    public void sendStatus(WebSocketSession session, boolean status) {
        SocketMessage socketMessage;
        if (status) {
            socketMessage = new SocketMessage("STATUS", "OK", SocketMessageType.STATUS);
        } else {
            socketMessage = new SocketMessage("STATUS", "ERR", SocketMessageType.STATUS);
        }
        try {
            session.sendMessage(new TextMessage(messageEncoder.encode(socketMessage)));
        } catch (IOException | EncodeException e) {
            throw new IllegalArgumentException("Could not send status message.");
        }
    }

    @Override
    public boolean handleSocketMessage(WebSocketSession session, SocketMessage socketMessage) throws EncodeException, IOException {
        System.out.println(socketMessage);
        if (socketMessage.getType().equalsIgnoreCase(SocketMessageType.ENCRYPTION_PUBLIC_KEY_EXCHANGE.toString())) {
            Integer userId = findByValue(sessionIdMap, session);
            List<Integer> otherSubscribers =  chatService.findUserIdByUserIdNotAndChatId(userId, Integer.parseInt(socketMessage.getParam1()));
            if (otherSubscribers.size() > 1) {
                throw new IllegalArgumentException("Secret chat features are not allowed for group chats");
            }
            WebSocketSession receiver = sessionIdMap.get(otherSubscribers.get(0));
            if (session != null) {
                receiver.sendMessage(new TextMessage(messageEncoder.encode(socketMessage)));
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }

    }


    @Override
    public void removeSession(WebSocketSession session) {
        if (session == null) {
            throw new IllegalArgumentException("Cannot remove an empty session.");
        }

        sessionIdMap.values().remove(session);
        try {
            session.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not close session.");
        }
    }

    @Override
    public void removeSession(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot remove an empty ID.");
        }

        try {
            WebSocketSession session = sessionIdMap.get(id);
            if (session != null) {
                session.close();
                sessionIdMap.remove(id);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not close session.");
        }
    }

    @Override
    public void sendCustomMessage(WebSocketSession session, String msg) {
        if (session == null || msg == null) {
            throw new IllegalArgumentException("Cannot send custom message with empty parameters.");
        }

        SocketMessage message = new SocketMessage(msg, SocketMessageType.NOTIFICATION);
        try {
            session.sendMessage(new TextMessage(messageEncoder.encode(message)));
        } catch (IOException | EncodeException e) {
            throw new IllegalArgumentException("Could not send custom message.");
        }
    }

    private Integer findByValue(Map<Integer, WebSocketSession> map, WebSocketSession value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

}
