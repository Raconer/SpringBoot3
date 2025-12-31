package com.spring.oauth.social.instagram.service;

import com.spring.oauth.social.instagram.dto.InstagramUser;
import com.spring.oauth.social.instagram.dto.response.InstagramTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
public class OauthInstagramService {

    // ================================================
    // application.yml에서 설정값 주입
    // ================================================

    @Value("${instagram.client-id}")
    private String clientId;  // Instagram 앱 ID (Meta 개발자 콘솔에서 발급)

    @Value("${instagram.client-secret}")
    private String clientSecret;  // Instagram 앱 시크릿 키 (절대 노출 금지)

    @Value("${instagram.redirect-uri}")
    private String redirectUri;  // 로그인 성공 후 돌아올 우리 서버 URL

    // HTTP 요청을 보내기 위한 Spring 유틸리티 클래스
    private final RestTemplate restTemplate = new RestTemplate();

    // ================================================
    // 1단계: Instagram 로그인 페이지 URL 생성
    // ================================================
    public String getAuthorizationUrl() {
        return "https://api.instagram.com/oauth/authorize"
                + "?client_id=" + clientId                    // 우리 앱 식별자
                + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)  // 로그인 후 돌아올 URL (URL 인코딩 필수)
                + "&scope=user_profile"                       // 요청할 권한 (프로필 정보만)
                + "&response_type=code"
                + "&state=어떤값";                      // 응답 타입: authorization code
    }

    // ================================================
    // 2단계: 전체 인증 프로세스 실행
    // ================================================
    public InstagramUser authenticate(String code) {
        // 1. code를 access_token으로 교환
        InstagramTokenResponse token = exchangeToken(code);

        // 2. access_token으로 사용자 정보 조회 후 반환
        return getUserInfo(token.getAccessToken());

    }

    // ================================================
    // 3단계: Authorization Code → Access Token 교환
    // ================================================
    private InstagramTokenResponse exchangeToken(String code) {
        // Instagram API에 보낼 파라미터 구성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);              // 앱 ID
        params.add("client_secret", clientSecret);      // 앱 시크릿 (서버 간 통신이므로 노출 안됨)
        params.add("grant_type", "authorization_code"); // OAuth 표준 값 (고정)
        params.add("redirect_uri", redirectUri);        // 최초 요청 시와 동일해야 함
        params.add("code", code);                       // Instagram에서 받은 코드

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);  // form 데이터 형식

        // Instagram 토큰 엔드포인트로 POST 요청
        ResponseEntity<InstagramTokenResponse> response = restTemplate.postForEntity(
                "https://api.instagram.com/oauth/access_token",  // Instagram 토큰 발급 URL
                new HttpEntity<>(params, headers),                // 요청 바디 + 헤더
                InstagramTokenResponse.class                      // 응답을 이 클래스로 변환
        );

        return response.getBody();
    }

    // ================================================
    // 4단계: Access Token으로 사용자 정보 조회
    // ================================================
    private InstagramUser getUserInfo(String accessToken) {
        // Instagram Graph API URL 구성
        String url = "https://graph.instagram.com/me?fields=id,username,account_type&access_token=" + accessToken;

        // GET 요청 → JSON 응답 → InstagramUser 객체로 자동 변환
        return restTemplate.getForObject(url, InstagramUser.class);
    }
}