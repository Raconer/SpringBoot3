package com.spring.oauth.social.instagram.controller;

import com.spring.oauth.social.instagram.dto.InstagramUser;
import com.spring.oauth.social.instagram.service.OauthInstagramService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/oauth/instagram")
@AllArgsConstructor
@Slf4j
public class OauthInstagramController {

    private final OauthInstagramService oauthInstagramService;

    /**
     * Instagram 로그인 페이지로 리다이렉트
     */
    @GetMapping()
    public ResponseEntity<Void> login() {
        String authUrl = oauthInstagramService.getAuthorizationUrl();

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(authUrl))
                .build();
    }

    /**
     * 콜백 → 인증 완료 → 사용자 정보 반환
     */
    @GetMapping("/callback")
    public ResponseEntity<InstagramUser> callback(@RequestParam String code) {
        InstagramUser user = oauthInstagramService.authenticate(code);
        log.info("Instagram 인증 성공: {}", user.getUsername());
        return ResponseEntity.ok(user);
    }
}
