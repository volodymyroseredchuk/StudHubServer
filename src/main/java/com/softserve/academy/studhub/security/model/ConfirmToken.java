package com.softserve.academy.studhub.security.model;

import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.constants.ExpirationConstants;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class ConfirmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private Date expiryDate;

    public ConfirmToken(User user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
        setExpiryDate(ExpirationConstants.CONFIRM_TOKEN_EXPIRATION_IN_MINUTES);
    }

    public ConfirmToken(){}

    public void setExpiryDate(int minutes) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minutes);
        this.expiryDate = now.getTime();
    }

    public boolean isExpired() {
        return new Date().after(this.expiryDate);
    }
}