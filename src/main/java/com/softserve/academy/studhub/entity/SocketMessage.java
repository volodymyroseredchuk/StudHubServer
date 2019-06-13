package com.softserve.academy.studhub.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SocketMessage {

    private String name;
    private String text;

}
