package com.softserve.academy.studhub.entity;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SocketMessage {

    private String subject_type = "Ok";
    private String id;

    public SocketMessage(String id) {
        this.id = id;
    }

}
