# ========================================================
# 1단계: 빌드 환경 (Builder Stage)
# ========================================================
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

# [핵심 변경 1]
# docker-compose에서 context를 루트(../../)로 설정할 것이므로,
# 여기서 COPY . . 을 하면 '프로젝트 전체 파일(모든 모듈 포함)'이 복사됩니다.
COPY . .

# [핵심 변경 2]
# 프로젝트 전체가 들어왔으니, Gradle이 의존성(jpa 모듈)을 찾을 수 있습니다.
# 전체 빌드를 수행합니다. (특정 모듈만 빌드하려면 :kotlin:kotlin-api:build 처럼 경로 지정 가능)
RUN gradle clean :kotlin-api:bootJar -x test --no-daemon

# ========================================================
# 2단계: 실행 환경 (Run Stage)
# ========================================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# [핵심 변경 3]
# 전체 프로젝트 구조 안에서 'kotlin-api'의 빌드 결과물(JAR)을 찾아 복사합니다.
# (경로가 깊어졌으니 주의하세요!)
COPY --from=builder /app/kotlin/kotlin-api/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]