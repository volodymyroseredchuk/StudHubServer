package com.softserve.academy.studhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "feeds")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "link")
    private String link;

    @LazyCollection(LazyCollectionOption.EXTRA)
    @ManyToMany(targetEntity = User.class, mappedBy = "feeds")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private List<User> userList;

}
