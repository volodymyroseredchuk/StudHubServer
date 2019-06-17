package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.service.SocketService;
import com.softserve.academy.studhub.service.SocketTokenService;
import com.softserve.academy.studhub.service.impl.SocketServiceImpl;
import com.softserve.academy.studhub.service.impl.SocketTokenServiceImpl;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;


public class WebSocketHandler extends AbstractWebSocketHandler {

    private SocketService socketService = new SocketServiceImpl();
    private SocketTokenService tokenService = new SocketTokenServiceImpl();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        socketService.sendNotification(session, message);

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        /*-----------------------*/
        String connMsg = "{\"name\":\"Greetings\",\"text\":\"Connected successfully.\\n        \"}";
        String noConnMsg = "{\"name\":\"Greetings\",\"text\":\"Connected unsuccessfully. Access denied.\\n        \"}";
        /*-----------------------*/

        try {
            String token = session.getUri().getQuery();

            if (tokenService.checkAccess(token)) {
                socketService.addSession(session);
                socketService.sendGreetings(session, new TextMessage(connMsg));
            } else {
                socketService.sendGreetings(session, new TextMessage(noConnMsg));
                session.close();
            }

        } catch (NullPointerException e) {
            socketService.sendGreetings(session, new TextMessage(noConnMsg));
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Client disconnected.");
        socketService.removeSession(session);
    }
}
