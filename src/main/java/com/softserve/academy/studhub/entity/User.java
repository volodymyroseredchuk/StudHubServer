package com.softserve.academy.studhub.entity;

import com.softserve.academy.studhub.entity.enums.Roles;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @EqualsAndHashCode.Exclude
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Roles role;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @EqualsAndHashCode.Exclude
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @EqualsAndHashCode.Exclude
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "university_id", referencedColumnName = "id")
    private University university;

    @EqualsAndHashCode.Exclude
    @Column(name = "image_url")
    private String imageUrl;

}
