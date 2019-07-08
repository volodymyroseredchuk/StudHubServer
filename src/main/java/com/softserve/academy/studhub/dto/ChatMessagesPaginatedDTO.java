package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.entity.SocketChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChatMessagesPaginatedDTO {

    private List<SocketChatMessage> messages;

    private Long messagesTotalCount;

}
