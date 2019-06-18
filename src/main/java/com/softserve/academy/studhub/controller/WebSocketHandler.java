package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.coders.SocketMessageDecoder;
import com.softserve.academy.studhub.entity.SocketMessage;
import com.softserve.academy.studhub.service.SocketService;
import com.softserve.academy.studhub.service.SocketTokenService;
import com.softserve.academy.studhub.service.SubscriptionService;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class WebSocketHandler extends AbstractWebSocketHandler {

    private SocketService socketService;

    private SocketTokenService tokenService;

    private SubscriptionService subscriptionService;

    private SocketMessageDecoder decoder = new SocketMessageDecoder();

    public WebSocketHandler(SocketService socketService, SocketTokenService tokenService,
                            SubscriptionService subscriptionService) {
        this.socketService = socketService;
        this.tokenService = tokenService;
        this.subscriptionService = subscriptionService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        SocketMessage msg = decoder.decode(message.getPayload());
        try {
            subscriptionService.handleMessage(msg);
        } catch (IllegalArgumentException e) {
            socketService.sendCustomMessage(session, e.getMessage());
        }

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        try {

            String token = session.getUri().getQuery();
            Integer id = tokenService.checkAccess(token);

            if (id > 0) {
                socketService.addSession(id, session);
                socketService.sendGreetings(session, 1);
            } else {
                socketService.sendGreetings(session, 2);
                session.close();
            }

        } catch (NullPointerException e) {
            socketService.sendGreetings(session, 2);
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        socketService.removeSession(session);
    }

}
