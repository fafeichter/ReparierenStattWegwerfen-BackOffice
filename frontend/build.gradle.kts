import com.github.gradle.node.npm.task.NpmTask

plugins {
    java
    id("com.github.node-gradle.node") version "7.1.0"
}

val skipFrontendBuild = project.findProperty("skipFrontendBuild")?.toString()?.toBoolean() ?: false

tasks.clean {
    delete("dist")
}

// Custom task to handle 'npm run test'
val npmTest = tasks.register<NpmTask>("npmTest") {
    description = "Runs Angular frontend unit tests"
    group = "verification"

    args.set(listOf("run", "test"))

    dependsOn(tasks.npmInstall, project(":backend").tasks.named("generateApiClients"))
}

// Bind the Angular test task to Gradle's native lifecycle
tasks.test {
    dependsOn(npmTest)
}

// Custom task to handle 'npm run prod'
val npmBuild = tasks.register<NpmTask>("npmBuild") {
    description = "Builds the frontend production assets"
    args.set(listOf("run", "prod"))
    dependsOn(tasks.npmInstall, project(":backend").tasks.named("generateApiClients"))
    onlyIf { !skipFrontendBuild }
}

// Tells Gradle to run the frontend build whenever a JAR is requested
tasks.jar {
    dependsOn(npmBuild)
    // Skips creating a jar if there are no compiled classes/resources
    onlyIf { !sourceSets["main"].output.isEmpty }
}