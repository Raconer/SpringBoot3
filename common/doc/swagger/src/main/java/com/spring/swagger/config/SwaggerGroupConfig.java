package com.spring.swagger.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerGroupConfig {
    // --------------------------------------------------------
    // [V1.0.0] 그룹
    // --------------------------------------------------------
    @Bean
    public GroupedOpenApi adminV1Group() {
        return GroupedOpenApi.builder()
                .group("V1.0.0") // 1단계: 버전
                .displayName("V1 API") // 2단계: 분류
                .pathsToMatch("/v1/**")
                .build();
    }

    // --------------------------------------------------------
    // [V2.0.0] 그룹
    // --------------------------------------------------------
    @Bean
    public GroupedOpenApi adminV2Group() {
        return GroupedOpenApi.builder()
                .group("V2.0.0") // 1단계: 버전
                .displayName("V2 API") // 2단계: 분류
                .pathsToMatch("/v2/**")
                .build();
    }
}
