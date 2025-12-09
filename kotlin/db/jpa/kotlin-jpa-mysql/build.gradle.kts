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
    // 공통 JPA 기반 모듈 (엔티티, Repository 등)
    api(project(":kotlin-jpa-core"))

    // MySQL JDBC 드라이버
    runtimeOnly("com.mysql:mysql-connector-j:$mysqlConnectorVersion")

    // Spring Test
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}