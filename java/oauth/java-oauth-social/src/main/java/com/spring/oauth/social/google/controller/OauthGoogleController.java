package com.spring.oauth.social.google.controller;

import com.spring.oauth.social.google.dto.GoogleUser;
import com.spring.oauth.social.google.service.OauthGoogleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/oauth/google")
@AllArgsConstructor
@Slf4j
public class OauthGoogleController {

    private final OauthGoogleService oauthGoogleService;

    /**
     * Instagram 로그인 페이지로 리다이렉트
     */
    @GetMapping()
    public ResponseEntity<Void> login() {
        String authUrl = oauthGoogleService.getAuthorizationUrl();
        System.out.println(authUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(authUrl))
                .build();
    }

    /**
     * 콜백 → 인증 완료 → 사용자 정보 반환
     */
    @GetMapping("/callback")
    public ResponseEntity<GoogleUser> callback(@RequestParam(name = "code") String code) {
        GoogleUser user = oauthGoogleService.authenticate(code);
        log.info("Google 인증 성공: {}", user.getEmail());
        return ResponseEntity.ok(user);
    }
}
