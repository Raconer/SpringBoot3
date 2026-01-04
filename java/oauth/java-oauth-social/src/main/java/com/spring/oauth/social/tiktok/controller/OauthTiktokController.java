package com.spring.oauth.social.tiktok.controller;

import com.spring.oauth.social.instagram.dto.InstagramUser;
import com.spring.oauth.social.instagram.service.OauthInstagramService;
import com.spring.oauth.social.tiktok.service.OauthTiktokService;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/oauth/tiktok")
@Slf4j
public class OauthTiktokController {
    @Value("${tiktok.verify}")
    private String TIKTOK_OAUTH_VERIFY;

    private OauthTiktokService oauthTiktokService;

    public OauthTiktokController(OauthTiktokService oauthTiktokService) {
        this.oauthTiktokService = oauthTiktokService;
    }

    @GetMapping(value =  "/verify/tiktoktgcnBO50zynx5uJlfpLxr8svUWUbskgV.txt", produces = "text/plain")
    public String verify() {
        return TIKTOK_OAUTH_VERIFY;
    }

    /**
     * Instagram 로그인 페이지로 리다이렉트
     */
    @GetMapping()
    public ResponseEntity<Void> login() {
        String authUrl = oauthTiktokService.getAuthorizationUrl();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(authUrl))
                .build();
    }

    /**
     * 콜백 → 인증 완료 → 사용자 정보 반환
     */
    @GetMapping("/callback")
    public ResponseEntity<InstagramUser> callback(@RequestParam(name = "code") String code) {
        InstagramUser user = oauthTiktokService.authenticate(code);
        log.info("Instagram 인증 성공: {}", user.getUsername());
        return ResponseEntity.ok(user);
    }

}
