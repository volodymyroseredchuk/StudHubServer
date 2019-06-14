package com.softserve.academy.studhub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @EqualsAndHashCode.Exclude
    @Column(name="body", columnDefinition = "text")
    private String body;

    @EqualsAndHashCode.Exclude
    @Column(name = "approved")
    private Boolean approved;

    @ManyToOne
    @JoinColumn(name="question_id", referencedColumnName = "id")
    @JsonBackReference
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @EqualsAndHashCode.Exclude
    @Column(name = "rate")
    private Integer rate;

}
