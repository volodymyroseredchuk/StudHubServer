package com.softserve.academy.studhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat_subscriptions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ChatSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "param2")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "param2")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "param2")
    private User user;

}
