pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        jcenter()
    }
}

rootProject.name = ("VersionFile")
include("example")
include("versionfile-api")
includeBuild("plugin")
