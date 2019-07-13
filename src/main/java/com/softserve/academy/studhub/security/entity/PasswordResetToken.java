package com.softserve.academy.studhub.security.entity;

import com.softserve.academy.studhub.constants.ValidationConstants;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.constants.ExpirationConstants;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @NotBlank(message = ValidationConstants.EMPTY_TOKEN)
    @Column(unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NonNull
    @Column
    private Date expiryDate;

    public PasswordResetToken(User user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
        setExpiryDate(ExpirationConstants.PASSWORD_RESET_TOKEN_EXPIRATION_IN_MINUTES);
    }

    public PasswordResetToken(){}

    public void setExpiryDate(int minutes) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minutes);
        this.expiryDate = now.getTime();
    }

    public boolean isExpired() {
        return new Date().after(this.expiryDate);
    }
}
