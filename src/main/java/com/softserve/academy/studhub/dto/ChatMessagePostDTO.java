package com.softserve.academy.studhub.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessagePostDTO {

    private Integer id;
    private String content;
    private Integer sender;
    private LocalDateTime creationDateTime;
    private Integer chat;

}
