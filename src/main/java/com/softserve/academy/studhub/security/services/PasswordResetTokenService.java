package com.softserve.academy.studhub.security.services;

import com.softserve.academy.studhub.security.model.PasswordResetToken;

public interface PasswordResetTokenService {

    PasswordResetToken findByValidToken(String token);

    PasswordResetToken save(PasswordResetToken passwordResetToken);

    void delete(PasswordResetToken passwordResetToken);
}
