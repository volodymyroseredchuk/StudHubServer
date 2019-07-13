package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.service.SocketService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SocketServiceImplTest {

    private SocketService socketService;

    @Mock
    private WebSocketSession session;
    @Mock
    private static Map<Integer, WebSocketSession> sessionIdMap;


    @Before
    public void initialize() {
        socketService = new SocketServiceImpl();
    }

    @After
    public void finalize() {
        socketService = null;
    }

    @Test
    public void addSession() {
        socketService.addSession(1, session);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addSessionNull() {

        socketService.addSession(null, null);
        socketService.addSession(1, null);

    }

    @Test
    public void sendNotificationPositive() {
        Integer userId = 1;
        TextMessage msg = new TextMessage("Message");
        when(sessionIdMap.containsKey(userId)).thenReturn(true);
        when(sessionIdMap.get(userId)).thenReturn(session);
        socketService.sendNotification(1, msg);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sendNotificationNull() {
        socketService.sendNotification(null, null);
        socketService.sendNotification(1, null);
        socketService.sendNotification(null, new TextMessage("123"));
    }

    @Test
    public void sendGreetingsPositive() {
        socketService.sendGreetings(session, 1);
        socketService.sendGreetings(session, 2);
        socketService.sendGreetings(session, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sendGreetingsNull() {

        socketService.sendGreetings(null, null);
        socketService.sendGreetings(session, null);
        socketService.sendGreetings(null, 1);

    }

    @Test
    public void removeSessionPositiveSession() {
        List<WebSocketSession> list = new ArrayList<>();
        list.add(session);
        when(sessionIdMap.values()).thenReturn(list);
        socketService.removeSession(session);
    }

    @Test
    public void removeSessionPositiveInteger() {
        List<WebSocketSession> list = new ArrayList<>();
        list.add(session);
        when(sessionIdMap.get(1)).thenReturn(session);
        socketService.removeSession(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeSessionNullInteger() {
        socketService.removeSession(new Integer(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeSessionNullSession() {

        socketService.removeSession((WebSocketSession) null);

    }

    @Test
    public void sendCustomMessagePositive() {
        socketService.sendCustomMessage(session, "Message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void sendCustomMessageNull() {

        socketService.sendCustomMessage(null, null);
        socketService.sendCustomMessage(session, null);
        socketService.sendCustomMessage(null, "123");

    }
}