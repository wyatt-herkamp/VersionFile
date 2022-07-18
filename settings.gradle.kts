pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = ("VersionFile")
include("example")
include("versionfile-api")
includeBuild("plugin")
