package org.kakara.versionfile;

public class VFExtension {

    private String classPackage = "";
    private String jarDirectory = "";
    private String dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private boolean compileIntoJar = false;
    private boolean addFileParser = false;

    public VFExtension() {

    }

    public VFExtension(String classPackage, String dateFormat, boolean compileIntoJar, boolean addFileParser) {
        this.classPackage = classPackage;
        this.dateFormat = dateFormat;
        this.compileIntoJar = compileIntoJar;
        this.addFileParser = addFileParser;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(String classPackage) {
        this.classPackage = classPackage;
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

    public boolean isAddFileParser() {
        return addFileParser;
    }

    public void setAddFileParser(boolean addFileParser) {
        this.addFileParser = addFileParser;
    }

    public void setJarDirectory(String jarDirectory) {
        this.jarDirectory = jarDirectory;
    }

    public String getJarDirectory() {
        return jarDirectory;
    }
}
