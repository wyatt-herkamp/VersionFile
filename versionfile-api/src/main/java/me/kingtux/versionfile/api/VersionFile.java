package me.kingtux.versionfile.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Will give you a object to read the version.properties if inside the jar
 */
public class VersionFile {
    private Properties properties;

    public VersionFile(Properties properties) {
        this.properties = properties;
    }

    public String getSRCHash() {
        return properties.getProperty("src.hash");
    }

    public static VersionFile getFile() {
        return getFile("");
    }

    public static VersionFile getFile(String jarDirectory) {
        InputStream resourceAsStream = VersionFile.class
                .getResourceAsStream("/" + jarDirectory + "/version.properties");
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new VersionFile(properties);
    }
}
