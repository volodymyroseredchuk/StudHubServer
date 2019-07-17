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
@Table(name = "universities")
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @EqualsAndHashCode.Exclude
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @EqualsAndHashCode.Exclude
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "name")
    private String name;

    @EqualsAndHashCode.Exclude
    @Column(name = "city")
    private String city;

    @EqualsAndHashCode.Exclude
    @Column(name = "image_url")
    private String imageUrl;

    @EqualsAndHashCode.Exclude
    @Column(name = "mark")
    private Double mark;
}
