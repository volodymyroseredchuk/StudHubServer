package com.softserve.academy.studhub.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @EqualsAndHashCode.Exclude
    @Column(columnDefinition = "TEXT", name = "body")
    private String body;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "source_url")
    private String sourceUrl;

}
