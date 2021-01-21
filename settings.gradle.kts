pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        jcenter()
    }
}

rootProject.name = ("VersionFile")
include("example")
includeBuild("plugin")
