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

import javax.websocket.EncodeException;
import java.io.IOException;

public class WebSocketHandler extends AbstractWebSocketHandler {

    private SocketService socketService;

    private SocketTokenService tokenService;

    private SocketMessageDecoder socketMessageDecoder = new SocketMessageDecoder();

    private SubscriptionService subscriptionService;

    public WebSocketHandler(SocketService socketService, SocketTokenService tokenService,
                            SubscriptionService subscriptionService) {
        this.socketService = socketService;
        this.tokenService = tokenService;
        this.subscriptionService = subscriptionService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {

        SocketMessage socketMessage = socketMessageDecoder.decode(message.getPayload());
        boolean status = false;
        try {
            status = socketService.handleSocketMessage(session, socketMessage);
        } catch (EncodeException | IOException e) {
            socketService.sendStatus(session, false);
        }
        socketService.sendStatus(session, status);

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

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

        } catch (NullPointerException | IOException e) {
            socketService.sendGreetings(session, 2);
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        socketService.removeSession(session);
    }


}
