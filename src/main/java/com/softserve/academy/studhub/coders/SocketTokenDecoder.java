package com.softserve.academy.studhub.coders;

import com.google.gson.Gson;
import com.softserve.academy.studhub.entity.SocketToken;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class SocketTokenDecoder implements Decoder.Text<SocketToken> {

    private static Gson GSON = new Gson();

    @Override
    public SocketToken decode(String s) throws DecodeException {
        return GSON.fromJson(s, SocketToken.class);
    }

    @Override
    public boolean willDecode(String s) {
        return s!=null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
