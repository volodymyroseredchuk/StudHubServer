package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.security.model.ConfirmToken;
import com.softserve.academy.studhub.security.repository.ConfirmTokenRepository;
import com.softserve.academy.studhub.security.services.ConfirmTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmTokenServiceImpl implements ConfirmTokenService {

    private final ConfirmTokenRepository confirmTokenRepository;

    @Override
    public ConfirmToken findByToken(String token) {
        return confirmTokenRepository.findByToken(token);
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
