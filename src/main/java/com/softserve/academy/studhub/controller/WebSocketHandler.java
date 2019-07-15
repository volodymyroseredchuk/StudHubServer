package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.coders.SocketChatMessageDecoder;
import com.softserve.academy.studhub.entity.ChatMessage;
import com.softserve.academy.studhub.service.ChatService;
import com.softserve.academy.studhub.service.SocketService;
import com.softserve.academy.studhub.service.SocketTokenService;
import com.softserve.academy.studhub.service.SubscriptionService;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

public class WebSocketHandler extends AbstractWebSocketHandler {

    private SocketService socketService;

    private SocketTokenService tokenService;

    private SubscriptionService subscriptionService;

    private ChatService chatService;

    private SocketChatMessageDecoder chatMessageDecoder = new SocketChatMessageDecoder();

    public WebSocketHandler(SocketService socketService, SocketTokenService tokenService,
                            SubscriptionService subscriptionService) {
        this.socketService = socketService;
        this.tokenService = tokenService;
        this.subscriptionService = subscriptionService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {

        /*try {
            SocketMessage msg = decoder.decode(message.getPayload());
            subscriptionService.handleMessage(msg);
        } catch (IllegalArgumentException e) {
            socketService.sendGreetings(session, 3);
        } catch (DecodeException e) {
            socketService.sendGreetings(session, 3);
        }*/

        ChatMessage chatMessage = chatMessageDecoder.decode(message.getPayload());
        chatService.handleChatMessage(chatMessage);

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
