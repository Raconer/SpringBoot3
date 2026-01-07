package com.spring.mail.sender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSender {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public boolean send( String to,
                         String subject,
                         String templatePath,
                         Context context){

        try {
            String htmlContent = templateEngine.process(templatePath, context);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("이메일 발송 실패: " + to, e);
        }

        return true;
    }
}
