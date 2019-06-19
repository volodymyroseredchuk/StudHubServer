package com.softserve.academy.studhub.coders;

import com.google.gson.Gson;
import com.softserve.academy.studhub.entity.SocketToken;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class SocketTokenEncoder implements Encoder.Text<SocketToken> {

    private static Gson GSON = new Gson();

    @Override
    public String encode(SocketToken socketToken) throws EncodeException {
        return GSON.toJson(socketToken);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
