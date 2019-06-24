package com.softserve.academy.studhub.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NonNull
    @Size(min=3, max = 16)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NonNull
    @Size(min=3, max = 16)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NonNull
    @Email
    @Size(min=3, max = 60)
    @EqualsAndHashCode.Exclude
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NonNull
    @Size(min=3, max = 16)
    @Column(name = "user_name", nullable = false, unique = true)
    private String username;

    @NonNull
    @EqualsAndHashCode.Exclude
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "university_id", referencedColumnName = "id")
    private University university;

    @EqualsAndHashCode.Exclude
    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @Column(name = "email_subscription", columnDefinition = "boolean default true")
    private Boolean emailSubscription;

}
