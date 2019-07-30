package com.softserve.academy.studhub.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"title", "user", "modifiedDate", "isPublic", "userList"})
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User user;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "public", columnDefinition = "boolean default true")
    private Boolean isPublic = true;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinTable(name = "teams_users", joinColumns = {@JoinColumn(name = "team_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> userList;
}
