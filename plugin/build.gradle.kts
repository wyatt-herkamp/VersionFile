plugins {
    java
    `java-library`
    `maven-publish`
    signing
    kotlin("jvm") version "1.7.20"

}

group = "me.kingtux.versionfile"
version = "2.0.0-SNAPSHOT"
sourceSets.main {
    java.srcDirs("src/main/myJava", "src/main/myKotlin")
}
kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
repositories {
    google()
    mavenCentral()
}

dependencies {
    compileOnly(gradleApi())

}
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            val baseName = project.group.toString();
            groupId = baseName
            artifactId = "$baseName.gradle.plugin"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            withoutBuildIdentifier()
        }
    }
    repositories {
        val releasesRepoUrl = uri("https://repo.kingtux.me/storages/maven/kingtux-repo")
        val snapshotsRepoUrl = uri("https://repo.kingtux.me/storages/maven/kingtux-repo")
        val url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        maven(url) {
            credentials(PasswordCredentials::class)
            name = "kingtuxrepo"
        }
        mavenLocal()
    }
}

