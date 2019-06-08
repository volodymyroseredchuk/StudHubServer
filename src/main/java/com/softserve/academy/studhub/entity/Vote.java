package com.softserve.academy.studhub.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "votes")
public class Vote {

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @EqualsAndHashCode.Exclude
    @Column(name = "value")
    @NotNull
    private int value;

    @EqualsAndHashCode.Exclude
    @Column(name = "user_id")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @EqualsAndHashCode.Exclude
    @Column(name = "answer_id")
    @ManyToOne
    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    private Answer answer;

    @EqualsAndHashCode.Exclude
    @Column(name = "feedback_id")
    @ManyToOne
    @JoinColumn(name = "feedback_id", referencedColumnName = "id")
    private Feedback feedback;

}