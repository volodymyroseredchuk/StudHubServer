package com.softserve.academy.studhub.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "param2")
    private Integer id;

    @Column(name = "value", nullable = false)
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "param2")
    private User user;

    @ManyToOne
    @JoinColumn(name = "answer_id", referencedColumnName = "param2")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "feedback_id", referencedColumnName = "param2")
    private Feedback feedback;

}