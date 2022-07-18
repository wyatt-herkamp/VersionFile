package me.kingtux.versionfile;


import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class VFMain implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        VFTask vfTask = new VFTask(target);
        target.task("vftask").doLast(task -> {
            vfTask.createFile(target);
        });
    }
}