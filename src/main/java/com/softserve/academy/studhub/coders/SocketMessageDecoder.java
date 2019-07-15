package com.softserve.academy.studhub.coders;

import com.google.gson.Gson;
import com.softserve.academy.studhub.entity.SocketMessage;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class SocketMessageDecoder implements Decoder.Text<SocketMessage> {

    private static Gson GSON = new Gson();

    @Override
    public SocketMessage decode(String s) {
        return GSON.fromJson(s, SocketMessage.class);
    }

    @Override
    public boolean willDecode(String s) {
        return s != null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

}
