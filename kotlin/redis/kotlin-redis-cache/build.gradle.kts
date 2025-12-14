val springBootVersion: String by rootProject.extra
val mysqlConnectorVersion: String by rootProject.extra

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("java-library")
    id("io.spring.dependency-management")
}

group = "com.spring"
description = "kotlin-jpa-mysql"

dependencies {
    api(project(":kotlin-redis-core"))

    // Spring Test
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}