package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.model.PasswordResetToken;

public interface EmailService {

    void sendResetPasswordEmail(User receiver, PasswordResetToken token);
}
