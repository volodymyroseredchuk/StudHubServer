package com.softserve.academy.studhub.coders;

import com.google.gson.Gson;
import com.softserve.academy.studhub.entity.SocketChatMessage;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class SocketChatMessageEncoder implements Encoder.Text<SocketChatMessage> {

    private static Gson GSON = new Gson();

    @Override
    public String encode(SocketChatMessage message) {
        return GSON.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

}
