package com.softserve.academy.studhub.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value", nullable = false)
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "feedback_id", referencedColumnName = "id")
    private Feedback feedback;

}