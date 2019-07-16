package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.exceptions.ExpiredTokenException;
import com.softserve.academy.studhub.security.entity.PasswordResetToken;
import com.softserve.academy.studhub.security.repository.PasswordResetTokenRepository;
import com.softserve.academy.studhub.security.services.PasswordResetTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public PasswordResetToken findByValidToken(String token) {

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).orElseThrow(
                () -> new ExpiredTokenException(ErrorMessage.PASSWORD_RESET_LINK_IS_EXPIRED_OR_INVALID));

        if (passwordResetToken.isExpired()) {

            delete(passwordResetToken);
            throw new ExpiredTokenException(ErrorMessage.PASSWORD_RESET_LINK_IS_EXPIRED_OR_INVALID);
        }

        return passwordResetToken;
    }

    @Override
    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        return passwordResetTokenRepository.saveAndFlush(passwordResetToken);
    }

    @Override
    public void delete(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.delete(passwordResetToken);
    }
}
