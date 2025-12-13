package com.spring.swagger.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${custom.swagger.title}")
    private String title;

    @Value("${custom.swagger.description}")
    private String description;

    @Value("${custom.swagger.version}")
    private String version;

    @Bean // 이 메서드가 반환하는 OpenAPI 객체를 스프링 빈으로 등록합니다.
    public OpenAPI customOpenAPI() {
        // --------------------------------------------------------
        // 서버 URL 객체 생성
        // --------------------------------------------------------
        Server localServer = new Server().url("http://localhost:8080").description("로컬 테스트 서버");

        // --------------------------------------------------------
        // 보안 설정 (JWT) - "자물쇠와 열쇠 구멍 만들기"
        // --------------------------------------------------------
        // 이 보안 설정의 고유한 별명(ID)입니다.
        String jwtSchemeName = "JWT Auth";

        // [보안 요구사항] - 이 API는 'jwtSchemeName'이라는 보안 방식이 필요하다"라고 선언하는 부분입니다.
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        // [보안 스키마 정의]
        // "구체적으로 어떻게 인증할 것인가?"를 설정하는 부분입니다.
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName) // 위에서 정한 이름표(ID)와 똑같아야 연결됩니다.
                        .type(SecurityScheme.Type.HTTP) // HTTP 프로토콜의 인증 방식을 사용합니다.
                        .scheme("bearer")  // "Authorization: Bearer <토큰>" 헤더 타입을 사용합니다.
                        .bearerFormat("JWT")); // (선택사항) 토큰의 형식이 JWT라고 명시합니다.

        // --------------------------------------------------------
        // OpenAPI 객체 생성 및 조합
        // --------------------------------------------------------
        return new OpenAPI()
                // [기본 정보]
                .info(new Info()
                        .title(title)  // 문서 제목 (HTML 상단에 표시)
                        .description(description) // 문서 설명
                        .version(version))           // API 버전
                .servers(List.of(localServer))
                // [보안 적용]- '보안 요구사항(자물쇠)'을 API 전체에 적용 ( 이걸 넣어야 우측 상단에 'Authorize' 버튼 생성)
                .addSecurityItem(securityRequirement)
                // [컴포넌트 등록] - 위에서 정의한 '보안 스키마(열쇠 구멍 설정)'를 문서에 등록
                .components(components);
    }
}
