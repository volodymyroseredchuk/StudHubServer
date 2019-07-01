package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.service.SocketService;
import lombok.AllArgsConstructor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketSessionDecorator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SocketServiceImplTest {

    @InjectMocks
    private SocketService socketService = new SocketServiceImpl();

    @Mock
    private WebSocketSession session;

    @After
    public void finalize() {
        socketService = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void addSessionNull() {

        socketService.addSession(null, null);
        socketService.addSession(1, null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void sendNotificationNull() {

        socketService.sendNotification(null, null);
        socketService.sendNotification(1, null);
        socketService.sendNotification(null, new TextMessage("123"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void sendGreetingsNull() {

        socketService.sendGreetings(null, null);
        socketService.sendGreetings(session, null);
        socketService.sendGreetings(null, 1);

    }

    @Test(expected = IllegalArgumentException.class)
    public void removeSessionNull() {

        socketService.removeSession(null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void sendCustomMessageNull() {

        socketService.sendCustomMessage(null, null);
        socketService.sendCustomMessage(session, null);
        socketService.sendCustomMessage(null, "123");

    }
}