package com.softserve.academy.studhub.coders;

import com.google.gson.Gson;
import com.softserve.academy.studhub.entity.ChatMessage;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class SocketChatMessageEncoder implements Encoder.Text<ChatMessage> {

    private static Gson GSON = new Gson();

    @Override
    public String encode(ChatMessage message) {
        return GSON.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

}
