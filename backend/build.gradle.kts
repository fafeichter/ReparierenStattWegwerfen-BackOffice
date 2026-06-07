import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    id("org.springframework.boot") version "4.0.6"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.hibernate.orm") version "7.2.12.Final"
    id("org.graalvm.buildtools.native") version "0.11.5"
}

val springAiVersion = "2.0.0-M8"
val springModulithVersion = "2.0.6"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-liquibase")
    implementation("org.springframework.boot:spring-boot-starter-restclient")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.modulith:spring-modulith-starter-core")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("com.mysql:mysql-connector-j")
    developmentOnly("org.springframework.ai:spring-ai-spring-boot-docker-compose")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-actuator-test")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
    testImplementation("org.springframework.boot:spring-boot-starter-liquibase-test")
    testImplementation("org.springframework.boot:spring-boot-starter-restclient-test")
    testImplementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server-test")
    testImplementation("org.springframework.boot:spring-boot-starter-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.ai:spring-ai-spring-boot-testcontainers")
    testImplementation("org.testcontainers:testcontainers-junit-jupiter")
    testImplementation("org.testcontainers:testcontainers-mysql")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    testCompileOnly("org.projectlombok:lombok")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testAnnotationProcessor("org.projectlombok:lombok")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:$springAiVersion")
        mavenBom("org.springframework.modulith:spring-modulith-bom:$springModulithVersion")
    }
}

hibernate {
    enhancement {
        enableAssociationManagement = true
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    reports {
        junitXml.required = true;
    }
}

// === AUTOMATED FRONTEND BUILD INTEGRATION ===
val copyFrontendAssets = tasks.register<Copy>("copyFrontendAssets") {
    description = "Copies production compiled frontend assets into the backend static resources"

    // Explicit cross-module dependency: tells Gradle to build the frontend first
    dependsOn(":frontend:npmBuild")

    from(project(":frontend").projectDir.resolve("dist/browser"))
    into(layout.buildDirectory.dir("resources/main/static"))
}

// Hooks the asset copy directly into Gradle's native compilation flow
tasks.processResources {
    dependsOn(copyFrontendAssets)
}

// === OCI CONTAINER BUILD (spring-boot-maven-plugin equivalent) ===
tasks.named<BootBuildImage>("bootBuildImage") {
    imageName = "registry.fabian-feichter.at/reparieren-statt-wegwerfen-backoffice"
    buildpacks = listOf(
        "urn:cnb:builder:paketo-buildpacks/java-native-image",
        "paketobuildpacks/health-checker:latest"
    )
    environment.put(
        "BP_NATIVE_IMAGE_BUILD_ARGUMENTS",
        $$"""--initialize-at-run-time=sun.security.util.Password$ConsoleHolder"""
    )
    environment.put("BP_HEALTH_CHECKER_ENABLED", "true")
    docker {
        imagePlatform = "linux/amd64"
    }
}