package com.softserve.academy.studhub.coders;

import com.google.gson.Gson;
import com.softserve.academy.studhub.entity.SocketMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class SocketMessageEncoder implements Encoder.Text<SocketMessage> {

    private static Gson GSON = new Gson();

    @Override
    public String encode(SocketMessage message) throws EncodeException {
        return GSON.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

}
