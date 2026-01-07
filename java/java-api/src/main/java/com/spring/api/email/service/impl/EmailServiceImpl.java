package com.spring.api.email.service.impl;

import com.spring.api.email.dto.request.EmailSendRequest;
import com.spring.api.email.service.EmailService;
import com.spring.mail.sender.EmailEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final ApplicationEventPublisher publisher;

    @Override
    public void sendEmail(EmailSendRequest request) {
        Context context = new Context();

        publisher.publishEvent(new EmailEvent(
                request.getEmail(),
                "이메일 전송테스트",
                "test-email.html",
                context
        ));
    }
}
