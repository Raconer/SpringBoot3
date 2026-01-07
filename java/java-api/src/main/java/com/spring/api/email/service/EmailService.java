package com.spring.api.email.service;

import com.spring.api.email.dto.request.EmailSendRequest;

public interface EmailService {
    void sendEmail(EmailSendRequest request);
}
