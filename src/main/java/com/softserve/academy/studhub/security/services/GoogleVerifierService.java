package com.softserve.academy.studhub.security.services;

public interface GoogleVerifierService {

    boolean isValidToken(String clientIdToken);

}
