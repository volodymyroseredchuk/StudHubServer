package com.softserve.academy.studhub.entity;

import com.softserve.academy.studhub.entity.Enums.Roles;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @EqualsAndHashCode.Exclude
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @EqualsAndHashCode.Exclude
    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "user_name")
    private String userName;

    @NonNull
    @EqualsAndHashCode.Exclude
    @Column(name = "password")
    private String password;

    @NonNull
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "university_id", referencedColumnName = "id")
    private University university;

    @EqualsAndHashCode.Exclude
    @Column(name = "image_url")
    private String imageUrl;

}
