package com.spring.oauth.social.instagram.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InstagramTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("user_id")
    private Long userId;
}
