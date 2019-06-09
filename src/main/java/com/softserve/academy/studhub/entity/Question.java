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
@EqualsAndHashCode(exclude = {"title", "body", "rate", "user", "answerList", "tagList"})
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(columnDefinition = "TEXT", name = "body")
    private String body;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "modified_date")
    private LocalDateTime modefiedDate;
    @Transient
    private Integer rate;
    @Column(name = "user_id")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questions")
    private List<Answer> answerList;
    @ManyToMany(targetEntity = Tag.class, fetch = FetchType.EAGER)
    @JoinTable(name = "questions_tags", joinColumns = {@JoinColumn(name = "question_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tagList;


}
