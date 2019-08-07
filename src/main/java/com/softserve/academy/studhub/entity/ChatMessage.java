package com.softserve.academy.studhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat_messages")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ChatMessage implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_user_id", referencedColumnName = "id")
    private User sender;

    @Column(name = "creation_datetime")
    private LocalDateTime creationDateTime;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

    public ChatMessage clone() {
        ChatMessage message = new ChatMessage();
        message.setId(this.id);
        message.setSender(this.sender);
        message.setChat(this.chat);
        message.setCreationDateTime(this.creationDateTime);
        message.setContent(this.content);
        return message;
    }

}
