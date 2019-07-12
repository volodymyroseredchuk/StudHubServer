package com.softserve.academy.studhub.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "param2")
    private Integer id;

    @EqualsAndHashCode.Exclude
    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @EqualsAndHashCode.Exclude
    @Column(name = "mark")
    private Integer mark;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "param2")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "university_id", referencedColumnName = "param2")
    private University university;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "param2")
    private User user;

    @EqualsAndHashCode.Exclude
    @Column(name = "rate")
    private Integer rate;
}

