package com.softserve.academy.studhub.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class SocketToken {

    private String token;

    public SocketToken(String token) {
        this.token = token;
    }

}
