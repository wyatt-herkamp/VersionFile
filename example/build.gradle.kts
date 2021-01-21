plugins {
    id("java")
    id("org.kakara.versionfile") version "1.0-SNAPSHOT"

}

group = "org.kakara"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    jcenter()
}

tasks {
    "jar"{
        dependsOn(project.tasks.getByName("vftask"));
    }
}