package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.model.PasswordResetToken;

public interface EmailService {

    void sendResetPasswordEmail(User receiver, PasswordResetToken token);
    void sendNotificationEmail(User receiver, Question question) throws RuntimeException;
}
