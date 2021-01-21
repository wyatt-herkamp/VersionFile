package org.kakara.versionfile;

import org.gradle.api.DefaultTask;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public abstract class VFTask extends DefaultTask {
    public VFTask() {
        setDescription("Creates the Version File");
    }


    @TaskAction
    public void createFile() {
        File buildDir = getProject().getBuildDir();
        File propertiesFile = new File(buildDir, "version.properties");
        try {
            propertiesFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();
        properties.setProperty("src.hash", hash());
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(propertiesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileWriter == null) {
            return;
        }
        try {
            properties.store(fileWriter, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String hash() {
        StringBuilder builder = new StringBuilder();
        getProject().getConvention().getPlugin(JavaPluginConvention.class).getSourceSets().forEach(sourceSet -> {
            sourceSet.getAllSource().forEach(file -> {
                MessageDigest messageDigest;
                try {
                    messageDigest = MessageDigest.getInstance("SHA-256");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    builder.append(getFileChecksum(messageDigest, file));
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            });
        });

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        String s = builder.toString();
        System.out.println("s = " + s);
        messageDigest.update(s.getBytes());
        byte[] digest = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
        //Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(file);

        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }

        //close the stream; We don't need it now.
        fis.close();

        //Get the hash's bytes
        byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }
}
