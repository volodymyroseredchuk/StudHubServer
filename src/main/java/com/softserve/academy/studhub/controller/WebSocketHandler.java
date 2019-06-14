package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.coders.SocketMessageDecoder;
import com.softserve.academy.studhub.coders.SocketMessageEncoder;
import com.softserve.academy.studhub.entity.SocketMessage;
import com.softserve.academy.studhub.service.SocketService;
import com.softserve.academy.studhub.service.impl.SocketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;


public class WebSocketHandler extends AbstractWebSocketHandler {

    private SocketService socketService = new SocketServiceImpl();
    private SocketMessageDecoder decoder = new SocketMessageDecoder();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        socketService.sendNotification(session, message);
        SocketMessage msg = decoder.decode(message.getPayload());

        System.out.println("Got a message: \n" + msg.toString());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        System.out.println("Client connected.");

        /*-----------------------*/
        String connMsg = "{\"name\":\"Greetings\",\"text\":\"Connected successfully.\\n        \"}";
        /*-----------------------*/

        socketService.addSession(session);
        socketService.sendGreetings(session, new TextMessage(connMsg));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Client disconnected.");
        socketService.removeSession(session);
    }
}
