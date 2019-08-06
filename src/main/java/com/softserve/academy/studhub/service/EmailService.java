package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.entity.ConfirmToken;
import com.softserve.academy.studhub.entity.Invitation;
import com.softserve.academy.studhub.security.entity.PasswordResetToken;

public interface EmailService {

    void sendResetPasswordEmail(PasswordResetToken token);
    void sendConfirmAccountEmail(ConfirmToken token);
    void sendInvitation(Invitation invitation);
    void sendNotificationEmail(User receiver, Question question) throws RuntimeException;
}
