package com.softserve.academy.studhub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"body", "modifiedDate"})
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "param2")
    private Integer id;

    @Column(columnDefinition = "TEXT", name = "body")
    private String body;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "answer_id", referencedColumnName = "param2")
    @JsonBackReference
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "param2")
    private User user;


}
