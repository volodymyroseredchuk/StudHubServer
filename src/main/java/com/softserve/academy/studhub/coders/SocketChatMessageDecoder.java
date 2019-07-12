package com.softserve.academy.studhub.coders;

import com.google.gson.Gson;
import com.softserve.academy.studhub.entity.ChatMessage;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class SocketChatMessageDecoder implements Decoder.Text<ChatMessage> {

    private static Gson GSON = new Gson();

    @Override
    public ChatMessage decode(String s) {
        return GSON.fromJson(s, ChatMessage.class);
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
