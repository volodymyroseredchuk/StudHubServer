package com.softserve.academy.studhub.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"title", "body", "modifiedDate", "expectedPrice", "deadlineDate", "proposalList"})
@Entity
@Table(name = "tasks")
public class Task {

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
    private LocalDateTime modifiedDate;

    @Column(name = "deadline_date")
    private LocalDateTime deadlineDate;

    @Column(name = "expected_price")
    private Integer expectedPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    @JsonManagedReference
    private List<Proposal> proposalList;

    @ManyToMany(targetEntity = Tag.class, fetch = FetchType.EAGER)
    @JoinTable(name = "tasks_tags", joinColumns = {@JoinColumn(name = "tasks_id")},
            inverseJoinColumns = {@JoinColumn(name = "tags_id")})
    private Set<Tag> tagList;
}
