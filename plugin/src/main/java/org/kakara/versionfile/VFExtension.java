package org.kakara.versionfile;

public class VFExtension {

    private String jarDirectory = "";
    private String dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private boolean compileIntoJar = false;

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
}
