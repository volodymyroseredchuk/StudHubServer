package com.softserve.academy.studhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softserve.academy.studhub.constants.ValidationConstants;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"firstName", "lastName", "password", "university", "emailSubscription",
        "imageUrl", "roles", "isActivated", "teamList", "cookiesCount"})
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NonNull
    @NotBlank(message = ValidationConstants.EMPTY_FIRSTNAME)
    @Size(min = ValidationConstants.FIRSTNAME_MIN_LENGTH, max = ValidationConstants.FIRSTNAME_MAX_LENGTH,
            message = ValidationConstants.INVALID_FIRSTNAME_LENGTH)
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @NotBlank(message = ValidationConstants.EMPTY_LASTNAME)
    @Size(min = ValidationConstants.LASTNAME_MIN_LENGTH, max = ValidationConstants.LASTNAME_MAX_LENGTH,
            message = ValidationConstants.INVALID_LASTNAME_LENGTH)
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @NotBlank(message = ValidationConstants.EMPTY_EMAIL)
    @Email(message = ValidationConstants.INVALID_EMAIL)
    @Size(min = ValidationConstants.EMAIL_MIN_LENGTH, max = ValidationConstants.EMAIL_MAX_LENGTH,
            message = ValidationConstants.INVALID_EMAIL_LENGTH)
    @Column(name = "email", unique = true)
    private String email;

    @NonNull
    @NotBlank(message = ValidationConstants.EMPTY_USERNAME)
    @Size(min = ValidationConstants.USERNAME_MIN_LENGTH, max = ValidationConstants.USERNAME_MAX_LENGTH,
            message = ValidationConstants.INVALID_USERNAME_LENGTH)
    @Column(name = "user_name", unique = true)
    private String username;

    @NonNull
    @NotBlank(message = ValidationConstants.EMPTY_PASSWORD)
    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Column(name = "google_password")
    private String googlePassword;

    @NonNull
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "university_id", referencedColumnName = "id")
    private University university;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "cookies_count")
    private Integer cookiesCount;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "email_subscription", columnDefinition = "boolean default true")
    private Boolean emailSubscription = true;

    @Column(name = "is_activated", columnDefinition = "boolean default false")
    private Boolean isActivated = false;

    @LazyCollection(LazyCollectionOption.EXTRA)
    @ManyToMany(targetEntity = Team.class, mappedBy = "userList")
    @JsonIgnore
    private List<Team> teamList;
}
