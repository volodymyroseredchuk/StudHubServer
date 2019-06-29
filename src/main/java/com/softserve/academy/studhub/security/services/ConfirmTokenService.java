package com.softserve.academy.studhub.security.services;

import com.softserve.academy.studhub.security.entity.ConfirmToken;

public interface ConfirmTokenService {

    ConfirmToken findByValidToken(String token);

    ConfirmToken save(ConfirmToken confirmToken);

    void delete(ConfirmToken confirmToken);
}
