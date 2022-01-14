plugins {
    id("java")
    id("java-library")
    id("com.gradle.plugin-publish") version "0.12.0"
    id("java-gradle-plugin")
}

group = "me.kingtux"
version = "1.0.0"

repositories {
    google()
    mavenCentral()
    jcenter()
}

gradlePlugin {
    plugins {
        create("me.kingtux.versionfile") {
            id = "me.kingtux.versionfile"
            implementationClass = "me.kingtux.versionfile.VFMain"
            version = "1.0.0-SNAPSHOT"
        }
    }
}

pluginBundle {
    vcsUrl = "https://github.com/wherkamp/VersionFile"
    website = "https://github.com/wherkamp/VersionFile"
    description = "Generates a Version File from your source"
    tags = listOf("version")

    plugins {
        getByName("me.kingtux.versionfile") {
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
apply(plugin = "com.gradle.plugin-publish")