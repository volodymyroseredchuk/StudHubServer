package com.softserve.academy.studhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"title", "user", "modifiedDate", "userList"})
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User user;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinTable(name = "teams_users", joinColumns = {@JoinColumn(name = "team_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> userList;
}
