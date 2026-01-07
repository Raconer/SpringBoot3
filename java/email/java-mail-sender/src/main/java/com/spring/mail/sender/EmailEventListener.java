package com.spring.mail.sender;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailEventListener {

    private final EmailSender emailSender;

    @Async
    @EventListener
    public void handle(EmailEvent event) {
        emailSender.send(
                event.to(),
                event.subject(),
                event.templatePath(),
                event.context()
        );
    }
}
