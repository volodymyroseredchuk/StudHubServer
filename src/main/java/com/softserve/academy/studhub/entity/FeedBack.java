package com.softserve.academy.studhub.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "feedback")
public class FeedBack {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "body")
    private String body;

    @Column(name = "mark")
    private int mark;

    @Column(name = "teacher_id")
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @Column(name = "university_id")
    @ManyToOne
    @JoinColumn(name = "university_id", referencedColumnName = "id")
    private University university;
}

