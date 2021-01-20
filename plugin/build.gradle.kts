plugins {
    id("java")
    id("java-library")
    id("com.gradle.plugin-publish") version "0.12.0"
    id("java-gradle-plugin")
}

group = "org.kakara"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    jcenter()
}

gradlePlugin {
    plugins {
        create("org.kakara.versionfile") {
            id = "org.kakara.versionfile"
            implementationClass = "org.kakara.versionfile.VFMain"
            version = "1.0-SNAPSHOT"
        }
    }
}

pluginBundle {
    vcsUrl = "https://github.com/kakaragame/VersionFile"
    description = ""
    tags = listOf("version")

    plugins {
        getByName("org.kakara.versionfile") {
            displayName = "VersionFile"
        }
    }
}
buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("com.gradle.publish:plugin-publish-plugin:0.12.0")
    }
}
