# VersionFile
Generates a Properties file to give details about jar version.


## Properties inside the file
* SRC Hash
* Git Commit Hash
* Git Branch
* Full Version(The Actual Version)
* Build Date/Time(UNIX TIME)


## How to use? 
### Kotlin DLS
```kotlin
//Plugin
id("org.kakara.versionfile") version "1.0-SNAPSHOT"
//Having it run
tasks {
    "jar"{
        dependsOn(project.tasks.getByName("vftask"));
    }
}
//Config
versionFileConfig {
    isCompileIntoJar = true;
}
```
