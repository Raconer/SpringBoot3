package com.spring.oauth.social.tiktok.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * TikTok OAuth 인증 서비스
 *
 * OAuth 2.0 플로우:
 * 1. 사용자 → TikTok 로그인 페이지로 이동 (getAuthorizationUrl)
 * 2. 로그인 성공 → TikTok이 우리 서버로 "code" 전달
 * 3. code → access_token 교환 (exchangeToken)
 * 4. access_token으로 사용자 정보 조회 (getUserInfo)
 */
@Service
@Slf4j
public class OauthTiktokService {

    // ================================================
    // application.yml에서 설정값 주입
    // ================================================

    @Value("${tiktok.client-id")
    private String clientId;  // TikTok Client Key (Developer Portal에서 발급)

    @Value("${tiktok.client.-ecret}")
    private String clientSecret;  // TikTok Client Secret (절대 노출 금지)

    @Value("${tiktok.redirect-uri}")
    private String redirectUri;  // 로그인 성공 후 돌아올 우리 서버 URL

    // HTTP 요청을 보내기 위한 Spring 유틸리티 클래스
    private final RestTemplate restTemplate = new RestTemplate();

    // ================================================
    // 1단계: TikTok 로그인 페이지 URL 생성
    // ================================================
    public String getAuthorizationUrl() {
        return "https://www.tiktok.com/v2/auth/authorize/" +
                "?client_key=" + clientId +
                "&scope=user.info.basic" +
                "&response_type=code" +
                "&redirect_uri=" + redirectUri +
                "&state=tiktok_oauth_state"; // CSRF 방지를 위한 상태값
    }

    // ================================================
    // 2단계: 전체 인증 프로세스 실행
    // ================================================
    public Map<String, Object> authenticate(String code) {
        // 1. code를 access_token으로 교환
        Map<String, Object> tokenResponse = exchangeToken(code);

        // 응답 데이터에서 access_token 추출 (틱톡 응답 구조에 맞춤)
        String accessToken = (String) tokenResponse.get("access_token");

        // 2. access_token으로 사용자 정보 조회 후 반환
        return getUserInfo(accessToken);
    }

    // ================================================
    // 3단계: Authorization Code → Access Token 교환
    // ================================================
    private Map<String, Object> exchangeToken(String code) {
        // TikTok API에 보낼 파라미터 구성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_key", clientId);            // 클라이언트 키
        params.add("client_secret", clientSecret);      // 클라이언트 시크릿
        params.add("code", code);                       // TikTok에서 받은 코드
        params.add("grant_type", "authorization_code"); // OAuth 표준 값 (고정)
        params.add("redirect_uri", redirectUri);        // 최초 요청 시와 동일해야 함

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // form 데이터 형식

        // TikTok 토큰 엔드포인트로 POST 요청
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://open.tiktokapis.com/v2/oauth/token/", // TikTok 토큰 발급 URL
                new HttpEntity<>(params, headers),              // 요청 바디 + 헤더
                Map.class                                       // 응답을 Map으로 변환
        );

        return response.getBody();
    }

    // ================================================
    // 4단계: Access Token으로 사용자 정보 조회
    // ================================================
    private Map<String, Object> getUserInfo(String accessToken) {
        // HTTP 헤더 설정 (Bearer 토큰 방식)
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Authorization: Bearer {access_token}

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // TikTok v2 User Info API URL 구성 (필요한 필드 명시 필수)
        String url = "https://open.tiktokapis.com/v2/user/info/?fields=open_id,union_id,avatar_url,display_name";

        // GET 요청 수행
        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        return response.getBody();
    }
}