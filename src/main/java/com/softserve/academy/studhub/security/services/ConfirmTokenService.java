package com.softserve.academy.studhub.security.services;

import com.softserve.academy.studhub.security.model.ConfirmToken;

public interface ConfirmTokenService {

    ConfirmToken findByValidToken(String token);

    ConfirmToken save(ConfirmToken confirmToken);

    void delete(ConfirmToken confirmToken);
}
