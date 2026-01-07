package com.spring.mail.sender;

import org.thymeleaf.context.Context;

public record EmailEvent(
        String to,
        String subject,
        String templatePath,
        Context context
) {
}