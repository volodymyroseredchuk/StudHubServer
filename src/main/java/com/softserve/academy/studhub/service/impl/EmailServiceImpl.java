package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.model.Mail;
import com.softserve.academy.studhub.security.model.PasswordResetToken;
import com.softserve.academy.studhub.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${client.host}")
    private String clientHost;

    @Override
    public void sendResetPasswordEmail(User receiver, PasswordResetToken token) {

        try {

            Map<String, Object> model = new HashMap<>();
            model.put("token", token);
            model.put("user", receiver);
            model.put("signature", "https://studhub.com");
            model.put("resetUrl", clientHost + "/password_reset?token=" + token.getToken());

            Mail mail = new Mail(
                    "no-reply@studhub-supp.com",
                    receiver.getEmail(),
                    "Password reset request",
                    model
            );

            MimeMessage message = createEmailTemplate(mail, "email-template");
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendNotificationEmail(User receiver, Question question) throws RuntimeException {

        try {

            Map<String, Object> model = new HashMap<>();
            model.put("user", receiver);
            model.put("signature", "https://studhub.com");
            model.put("questionUrl", clientHost + "/questions/" + question.getId());
            model.put("questionName", question.getTitle());

            Mail mail = new Mail(
                    "no-reply@studhub-supp.com",
                    receiver.getEmail(),
                    "Question news",
                    model
            );

            MimeMessage message = createEmailTemplate(mail, "email-notification");
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private MimeMessage createEmailTemplate(Mail mail, String templateName) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariables(mail.getModel());
        String html = templateEngine.process("email/" + templateName, context);

        helper.setFrom(username);
        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());

        return message;
    }

}
