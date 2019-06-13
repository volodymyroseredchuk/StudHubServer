package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.coders.SocketMessageDecoder;
import com.softserve.academy.studhub.coders.SocketMessageEncoder;
import com.softserve.academy.studhub.entity.SocketMessage;
import com.softserve.academy.studhub.service.SocketService;
import com.softserve.academy.studhub.service.impl.SocketServiceImpl;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;


public class WebSocketHandler extends AbstractWebSocketHandler {

    SocketService socketService = new SocketServiceImpl();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        socketService.sendNotification(session, message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        /*-----------------------*/
        String connMsg = "{\"name\":\"admin\",\"text\":\"Connected successfully.\\n        \"}";
        /*-----------------------*/

        socketService.addSession(session);
        socketService.sendGreetings(session, new TextMessage(connMsg));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        socketService.removeSession(session);
    }
}
