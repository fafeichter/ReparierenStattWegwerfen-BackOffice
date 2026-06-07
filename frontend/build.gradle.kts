import com.github.gradle.node.npm.task.NpmTask

plugins {
  java
  id("com.github.node-gradle.node") version "7.1.0"
}

val skipFrontendBuild = project.findProperty("skipFrontendBuild")?.toString()?.toBoolean() ?: false

node {
  version.set("26.3.0")
  npmVersion.set("11.16.0")
  download.set(true)
  nodeProjectDir.set(projectDir)
}

tasks.clean {
  delete("dist")
}

// Custom task to handle 'npm run test'
val npmTest = tasks.register<NpmTask>("npmTest") {
  description = "Runs Angular frontend unit tests"
  group = "verification"

  args.set(listOf("run", "test"))

  dependsOn(tasks.npmInstall)
}

// Bind the Angular test task to Gradle's native lifecycle
tasks.test {
  dependsOn(npmTest)
}

// Custom task to handle 'npm run prod'
val npmBuild = tasks.register<NpmTask>("npmBuild") {
  description = "Builds the frontend production assets"
  args.set(listOf("run", "prod"))
  dependsOn(tasks.npmInstall)

  onlyIf { !skipFrontendBuild }
}

// Conditional execution for npmInstall based on the property flag
tasks.npmInstall {
  onlyIf { !skipFrontendBuild }
}

// Tells Gradle to run the frontend build whenever a JAR is requested
tasks.jar {
  dependsOn(npmBuild)
  // Skips creating a jar if there are no compiled classes/resources
  onlyIf { !sourceSets["main"].output.isEmpty }
}
