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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @EqualsAndHashCode.Exclude
    @Column(columnDefinition = "TEXT", name = "body")
    private String body;

    @EqualsAndHashCode.Exclude
    @Column(name = "mark")
    private Integer mark;

    @Column(name = "teacher_id")
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @Column(name = "university_id")
    @ManyToOne
    @JoinColumn(name = "university_id", referencedColumnName = "id")
    private University university;
}

