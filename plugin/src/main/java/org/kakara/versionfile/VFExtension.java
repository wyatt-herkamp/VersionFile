package org.kakara.versionfile;

import java.util.HashMap;
import java.util.Map;

public class VFExtension {

    private String jarDirectory = "";
    private String dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private boolean compileIntoJar = false;
    private Map<String, String> customValues = new HashMap<>();

    public VFExtension() {

    }

    public VFExtension(String dateFormat, boolean compileIntoJar) {
        this.dateFormat = dateFormat;
        this.compileIntoJar = compileIntoJar;
    }


    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public boolean isCompileIntoJar() {
        return compileIntoJar;
    }

    public void setCompileIntoJar(boolean compileIntoJar) {
        this.compileIntoJar = compileIntoJar;
    }


    public void setJarDirectory(String jarDirectory) {
        this.jarDirectory = jarDirectory;
    }

    public String getJarDirectory() {
        return jarDirectory;
    }

    public Map<String, String> getCustomValues() {
        return customValues;
    }

    public void setCustomValues(Map<String, String> customValues) {
        this.customValues = customValues;
    }
}
