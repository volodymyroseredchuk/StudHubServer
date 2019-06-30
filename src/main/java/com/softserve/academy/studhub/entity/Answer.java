package com.softserve.academy.studhub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answer")
    @JsonManagedReference
    @OrderBy(value = "creationDate DESC ")
    private List<Comment> comment;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @EqualsAndHashCode.Exclude
    @Column(name = "rate", columnDefinition = "int default 0")
    private Integer rate;

}
