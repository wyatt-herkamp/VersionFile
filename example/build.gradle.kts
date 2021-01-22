plugins {
    id("java")
    id("org.kakara.versionfile") version "1.0-SNAPSHOT"

}

group = "org.kakara"
version = "1.0.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    jcenter()
}
versionFileConfig {
    isCompileIntoJar = true;
    customValues["custom.key"] = "true";
}
tasks {
    "jar"{
        dependsOn(project.tasks.getByName("vftask"));
    }
}