package com.softserve.academy.studhub.dto;

public interface ChatListItemDTO {

    Integer getChatId();
    Boolean getSecret();
    String getLastMessageText();
    String getUsername();
    String getPhotoUrl();

}
