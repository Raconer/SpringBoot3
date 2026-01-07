package com.spring.api.email.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "이메일 발송 요청 정보")
public class EmailSendRequest {
    @Schema(description = "수신자 이메일 주소", example = "user@example.com")
    String email;
}
