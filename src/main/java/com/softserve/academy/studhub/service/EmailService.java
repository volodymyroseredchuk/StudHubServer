package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.security.model.Mail;

public interface EmailService {

    void sendEmail(Mail mail);
}
