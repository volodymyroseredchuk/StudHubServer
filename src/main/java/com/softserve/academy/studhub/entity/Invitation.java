package com.softserve.academy.studhub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.softserve.academy.studhub.security.constants.ExpirationConstants;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@Builder
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NonNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @NonNull
    @Column
    private Date expiryDate;

    @Tolerate
    public Invitation(){}

    public static InvitationBuilder builder() {
        return new CustomInvitationBuilder();
    }

    public boolean isExpired() {
        return new Date().after(this.expiryDate);
    }

    private static class CustomInvitationBuilder extends InvitationBuilder {

        @Override
        public Invitation build() {

            expiryDate(calcExpiryDate(ExpirationConstants.INVITATION_EXPIRATION_IN_MINUTES));
            return super.build();
        }

        public Date calcExpiryDate(int minutes) {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.MINUTE, minutes);
            return now.getTime();
        }
    }

}
