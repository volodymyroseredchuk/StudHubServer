package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.exceptions.ExpiredTokenException;
import com.softserve.academy.studhub.security.entity.ConfirmToken;
import com.softserve.academy.studhub.security.repository.ConfirmTokenRepository;
import com.softserve.academy.studhub.security.services.ConfirmTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmTokenServiceImpl implements ConfirmTokenService {

    private final ConfirmTokenRepository confirmTokenRepository;

    @Override
    public ConfirmToken findByValidToken(String token) {

        ConfirmToken confirmToken = confirmTokenRepository.findByToken(token).orElseThrow(() ->
                new ExpiredTokenException(ErrorMessage.LINK_IS_EXPIRED_OR_INVALID));

        if (confirmToken.isExpired()) {

            delete(confirmToken);
            throw new ExpiredTokenException(ErrorMessage.LINK_IS_EXPIRED_OR_INVALID);
        }

        return confirmToken;
    }

    @Override
    public ConfirmToken save(ConfirmToken confirmToken) {
        return confirmTokenRepository.saveAndFlush(confirmToken);
    }

    @Override
    public void delete(ConfirmToken confirmToken) {
        confirmTokenRepository.delete(confirmToken);
    }
}
