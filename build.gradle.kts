allprojects {
    group = "at.reparieren-statt-wegwerfen"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

// Shared configuration applied ONLY to submodules that use Java (like backend)
subprojects {
    plugins.withType<JavaPlugin> {
        configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(25))
            }
        }
    }
}