package com.softserve.academy.studhub.entity;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SocketMessage {

    private String name = "Ok";
    private String text;

    public SocketMessage(String text) {
        this.text = text;
    }

}
