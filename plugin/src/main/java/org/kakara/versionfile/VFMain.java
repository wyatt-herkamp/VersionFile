package org.kakara.versionfile;


import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class VFMain implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        var extension = target.getExtensions().create("versionFileConfig",VFExtension.class, target);
        target.getTasks().create("vftask",VFTask.class);

    }
}