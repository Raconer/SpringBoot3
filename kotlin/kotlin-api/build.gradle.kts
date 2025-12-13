val springBootVersion: String by rootProject.extra

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.8"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.spring"
description = "kotlin-api"

dependencies {
    // DB 접근(엔티티 + JPA Repository + MySQL 구현체)
    implementation(project(":kotlin-jpa-mysql"))

    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // SWAGGER
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}