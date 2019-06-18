package com.softserve.academy.studhub.config;

import com.softserve.academy.studhub.controller.WebSocketHandler;
import com.softserve.academy.studhub.service.SocketService;
import com.softserve.academy.studhub.service.SocketTokenService;
import com.softserve.academy.studhub.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@AllArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig  implements WebSocketConfigurer {

    private SocketService socketService;

    private SocketTokenService tokenService;

    private SubscriptionService subscriptionService;

    @Bean
    public ServletServerContainerFactoryBean createWebSocketConatainer() {

        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxBinaryMessageBufferSize(1024000);
        return container;

    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler(socketService, tokenService, subscriptionService), "/sock").setAllowedOrigins("*");
    }



}
