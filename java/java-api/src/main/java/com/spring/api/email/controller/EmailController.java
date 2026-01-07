package com.spring.api.email.controller;

import com.spring.api.email.dto.request.EmailSendRequest;
import com.spring.api.email.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Email API", description = "이메일 전송 관련 API")
@RestController
@RequestMapping("/v1/email")
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "인증 이메일 발송", description = "사용자가 입력한 이메일 주소로 인증 링크를 포함한 메일을 발송합니다.")
    @PostMapping
    public String sendEmail(@RequestBody EmailSendRequest request) {
        this.emailService.sendEmail(request);
        return "Success";
    }
}