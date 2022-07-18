plugins {
    id("java-gradle-plugin")
    java
    `java-library`
    `maven-publish`
    signing
}

group = "me.kingtux"
version = "1.0.0"

repositories {
    google()
    mavenCentral()
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

publishing {
    publications {
        create<MavenPublication>("mavenJava") {

            artifactId = "version-file"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("Version File")
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = uri("https://repo.kingtux.me/storages/maven/kingtux-repo")
            val snapshotsRepoUrl = uri("https://repo.kingtux.me/storages/maven/kingtux-repo")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials(PasswordCredentials::class)
        }
        mavenLocal()
    }
}

