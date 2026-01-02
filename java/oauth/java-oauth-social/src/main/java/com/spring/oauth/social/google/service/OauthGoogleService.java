package com.spring.oauth.social.google.service;

import com.spring.oauth.social.google.dto.GoogleUser;
import com.spring.oauth.social.google.dto.response.GoogleTokenResponse;
import com.spring.oauth.social.instagram.dto.response.InstagramTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Instagram OAuth 인증 서비스
 *
 * OAuth 2.0 플로우:
 * 1. 사용자 → Instagram 로그인 페이지로 이동 (getAuthorizationUrl)
 * 2. 로그인 성공 → Instagram이 우리 서버로 "code" 전달
 * 3. code → access_token 교환 (exchangeToken)
 * 4. access_token으로 사용자 정보 조회 (getUserInfo)
 */
@Service
public class OauthGoogleService {

    // ================================================
    // application.yml에서 설정값 주입
    // ================================================

    @Value("${google.client-id}")
    private String clientId;  // Instagram 앱 ID (Meta 개발자 콘솔에서 발급)

    @Value("${google.client-secret}")
    private String clientSecret;  // Instagram 앱 시크릿 키 (절대 노출 금지)

    @Value("${google.redirect-uri}")
    private String redirectUri;  // 로그인 성공 후 돌아올 우리 서버 URL

    // HTTP 요청을 보내기 위한 Spring 유틸리티 클래스
    private final RestTemplate restTemplate = new RestTemplate();

    // ================================================
    // 1단계: Instagram 로그인 페이지 URL 생성
    // ================================================
    public String getAuthorizationUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth?" +
                "response_type=code&" + // Authorization Code Grant 방식 사용. 인증 후 authorization code 반환
                "client_id=" + clientId + // Google에 등록된 애플리케이션 식별자
                "&redirect_uri=" + redirectUri + // 인증 완료 후 code를 전달받을 콜백 URL
                  "&state=security_token%3D138r5719ru3e1%26url%3Dhttps%3A%2F%2Foauth2-login-demo.example.com%2FmyHome" +  // CSRF 방지용 토큰 + 인증 후 리다이렉트할 최종 URL 포함
                // "&login_hint=jsmith@example.com" + // 로그인 화면에 미리 채워질 이메일 (UX 개선용)
                 "&nonce=0394852-3190485-2490358" + // ID Token 재사용 공격 방지용 일회성 값
                "&hd=example.com" + // Google Workspace 도메인 제한. 해당 도메인 계정만 로그인 허용
                "&scope=openid%20email"; // 요청 권한 범위. OpenID Connect 인증 + 이메일 정보 접근
    }

    // ================================================
    // 2단계: 전체 인증 프로세스 실행
    // ================================================
    public GoogleUser authenticate(String code) {
        // 1. code를 access_token으로 교환
        GoogleTokenResponse token = exchangeToken(code);

        // 2. access_token으로 사용자 정보 조회 후 반환
        return getUserInfo(token.getAccessToken());
    }

    // ================================================
// 3단계: Authorization Code → Access Token 교환
// ================================================
    private GoogleTokenResponse exchangeToken(String code) {
        // Google OAuth API에 보낼 파라미터 구성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);              // 클라이언트 ID
        params.add("client_secret", clientSecret);      // 클라이언트 시크릿
        params.add("grant_type", "authorization_code"); // OAuth 표준 값 (고정)
        params.add("redirect_uri", redirectUri);        // 최초 요청 시와 동일해야 함
        params.add("code", code);                       // Google에서 받은 코드

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);  // form 데이터 형식

        // Google 토큰 엔드포인트로 POST 요청
        ResponseEntity<GoogleTokenResponse> response = restTemplate.postForEntity(
                "https://oauth2.googleapis.com/token",  // Google 토큰 발급 URL
                new HttpEntity<>(params, headers),       // 요청 바디 + 헤더
                GoogleTokenResponse.class                // 응답을 이 클래스로 변환
        );

        return response.getBody();
    }

    // ================================================
// 4단계: Access Token으로 사용자 정보 조회
// ================================================
    private GoogleUser getUserInfo(String accessToken) {
        // HTTP 헤더에 Bearer 토큰 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        // Google userinfo 엔드포인트로 GET 요청
        ResponseEntity<GoogleUser> response = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo",  // Google 사용자 정보 URL
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GoogleUser.class
        );

        return response.getBody();
    }
}