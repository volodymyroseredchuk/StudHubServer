package com.softserve.academy.studhub.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="body", columnDefinition = "text")
    private String body;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "question_id")
    @ManyToOne
    @JoinColumn(name="question_id", referencedColumnName = "id")
    private Question question;

    @Column(name = "user_id")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Transient
    private Integer rate;

}
