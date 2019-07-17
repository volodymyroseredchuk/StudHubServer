package com.softserve.academy.studhub.entity;

import com.softserve.academy.studhub.entity.enums.SocketMessageType;
import lombok.*;

@Data
public class SocketMessage {

    private String param1;
    private String param2;
    private String type;

    public SocketMessage(String param2, SocketMessageType type) {
        if (type.equals(SocketMessageType.NOTIFICATION)) {
            this.param1 = "Ok";
            this.param2 = param2;
            this.type = type.toString();
        }
    }

    public SocketMessage(String param1, String param2, SocketMessageType type) {
        this.param1 = param1;
        this.param2 = param2;
        this.type = type.toString();
    }

}
