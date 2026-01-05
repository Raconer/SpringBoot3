package com.spring.oauth.social.tiktok.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TiktokUser {
    private String sub;            // Google 고유 사용자 ID
    private String email;

    @JsonProperty("email_verified")
    private Boolean emailVerified;

    private String name;           // 전체 이름

    @JsonProperty("given_name")
    private String givenName;      // 이름

    @JsonProperty("family_name")
    private String familyName;     // 성

    private String picture;        // 프로필 이미지 URL
    private String locale;         // 언어 설정
}
