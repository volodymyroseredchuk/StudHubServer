package com.softserve.academy.studhub.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class SocketMessage {

    private String name;
    private String text;

}
