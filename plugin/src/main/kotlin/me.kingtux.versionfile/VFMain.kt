package me.kingtux.versionfile

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class VFMain : Plugin<Project> {
    override fun apply(target: Project) {
        val vfTask = VFTask(target)
        target.task("vftask").doLast { vfTask.createFile(target) }
    }
}