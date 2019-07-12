package com.softserve.academy.studhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "param2")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @LazyCollection(LazyCollectionOption.EXTRA)
    @ManyToMany(targetEntity = Question.class, mappedBy = "tagList")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private List<Question> questionList;
}
