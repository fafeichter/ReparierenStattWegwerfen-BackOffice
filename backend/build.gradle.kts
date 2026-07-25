import org.openapitools.generator.gradle.plugin.tasks.GenerateTask
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    id("org.springframework.boot") version "4.1.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.hibernate.orm") version "7.4.4.Final"
    id("org.graalvm.buildtools.native") version "1.1.3"
    // Plugin to generate TypeScript code from OpenAPI JSON file
    id("org.openapi.generator") version "7.17.0"
    id("io.github.redgreencoding.plantuml") version "0.3.0"
}

extra["springAiVersion"] = "2.0.0"
extra["springModulithVersion"] = "2.1.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-liquibase")
    implementation("org.springframework.boot:spring-boot-starter-restclient")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.modulith:spring-modulith-starter-core")
    implementation("org.springframework.modulith:spring-modulith-starter-jdbc")
    implementation("org.springframework.modulith:spring-modulith-runtime")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.2")
    implementation("org.springframework.ai:spring-ai-starter-model-openai")
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    compileOnly("org.projectlombok:lombok")
    compileOnly("org.hibernate.orm:hibernate-processor")
    annotationProcessor("org.hibernate.orm:hibernate-processor")
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
    testImplementation("org.springframework.boot:spring-boot-starter-mustache-test")
    testCompileOnly("org.projectlombok:lombok")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testAnnotationProcessor("org.projectlombok:lombok")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
        mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
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
// Since bootBuildImage builds from the bootJar output, it inherits the assets automatically
val copyFrontendAssets = tasks.register<Copy>("copyFrontendAssets") {
    description = "Copies production compiled frontend assets into the backend static resources"

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

val generateOpenApiSpecs = tasks.register<Test>("generateOpenApiSpecs") {
    group = "openapi"
    description = "Generates OpenAPI JSON definitions"

    // Wire the custom Test task to the test source set
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    filter {
        // leading wildcard so it matches regardless of package
        includeTestsMatching("*OpenApiSpecsGenerator.generateOpenApiSpecs")
    }

    // The test produces files as a side effect; declare them so Gradle
    // tracks the output and re-runs when they're missing
    outputs.dir(layout.buildDirectory.dir("openapi"))

    // The test produces files as a side effect; Gradle should not track
    // the output and re-generate the files every time
    outputs.upToDateWhen { false }
}

// 2. Dynamic generation of TS clients
val apiModules = listOf("model", "businesspartner", "device", "sale")

val apiClientsTasks = apiModules.map { module ->
    val capitalizedModule = module.replaceFirstChar { it.uppercase() }

    tasks.register<GenerateTask>("generate${capitalizedModule}ApiClients") {
        group = "openapi"
        description = "Generates the ${module} TypeScript clients."

        generatorName = "typescript-angular"

        // Point to the location where our test writes the file
        inputSpec = "${layout.buildDirectory.get()}/openapi/${module}.json"
        // Out of src/, into a sibling dir inside frontend so the TS
        // toolchain can still resolve it via a path alias
        outputDir = "${project.rootDir}/frontend/build/generated/openapi/api/${module}"

        configOptions =
            mapOf(
                "supportsES6" to "true",
                "providedInRoot" to "true",
                "apiNameSuffix" to "${module.replaceFirstChar { it.uppercase() }}Service"
            )

        dependsOn(generateOpenApiSpecs)
    }
}

tasks.register("generateApiClients") {
    group = "openapi"
    description = "Generates all TypeScript API clients."

    dependsOn(apiClientsTasks)
}

plantuml {
    options {
        // where should the .svg be generated to (defaults to build/plantuml)
        outputDir = project.file("docs/spring-modulith/")

        // output format (lowercase, defaults to svg)
        format = "svg"
    }

    diagrams {
        create("modules") {
            // .puml sourcefile, this can be also omitted and defaults to _<name>.puml_.
            sourceFile = project.file("${project.layout.buildDirectory.get()}/spring-modulith-docs/components.puml")
        }
    }
}

tasks.register("generate") {
    group = "openapi"
    description = "Generates all TypeScript API clients."

    dependsOn(apiClientsTasks)
}

val generateSpringModulithPuml = tasks.register<Test>("generateSpringModulithPuml") {
    group = "documentation"
    description = "Runs only the Spring Modulith documentation tests to generate PUML files."

    // Wire the custom Test task to the test source set
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    filter {
        // leading wildcard so it matches regardless of package
        includeTestsMatching("*DocumentationTests.writeDocumentationSnippets")
    }

    // The test produces files as a side effect; Gradle should not track
    // the output and re-generate the files every time
    outputs.upToDateWhen { false }
}

tasks.register("generateModulithDoc") {
    group = "documentation"
    description = "Generates the module dependency SVG for the README"

    dependsOn(generateSpringModulithPuml)
    dependsOn("plantumlModules")
}