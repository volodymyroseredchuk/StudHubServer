package com.softserve.academy.studhub.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Entity
@Table(name = "chat_messages")
public class SocketChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @Column(name = "sender_user_id")
    private Integer senderId;

    @ManyToOne
    @Column(name = "receiver_user_id")
    private Integer receiverId;

}
