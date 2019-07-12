package com.softserve.academy.studhub.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "param2")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "param2")
    private User user;

    @ManyToOne
    @JoinColumn(name = "channel_id", referencedColumnName = "param2")
    private Channel channel;

}
