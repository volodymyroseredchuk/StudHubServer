package com.softserve.academy.studhub.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "param2")
    private Integer id;

    //TODO: remove
    @EqualsAndHashCode.Exclude
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @EqualsAndHashCode.Exclude
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @EqualsAndHashCode.Exclude
    @Column(name = "first_name")
    private String firstName;
    //TODO: frist and last name ??? exculde?
    @EqualsAndHashCode.Exclude
    @Column(name = "last_name")
    private String lastName;

    @EqualsAndHashCode.Exclude
    @Column(name = "image_url")
    private String imageUrl;

    //TODO: add exclude
    @ManyToOne
    @JoinColumn(name = "university_id", referencedColumnName = "param2")
    private University university;

    @EqualsAndHashCode.Exclude
    @Column(name = "mark")
    private Double mark;
}
