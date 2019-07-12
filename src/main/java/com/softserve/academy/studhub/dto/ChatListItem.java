package com.softserve.academy.studhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatListItem {

    private Integer chatId;
    private String photoUrl;
    private String username;
    private String lastMessageText;

}
