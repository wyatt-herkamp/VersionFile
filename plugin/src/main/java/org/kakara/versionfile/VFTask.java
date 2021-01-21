package org.kakara.versionfile;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaPluginConvention;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class VFTask {
    private VFExtension extension;

    public VFTask(Project project) {
        extension = project.getExtensions().create("versionFileConfig", VFExtension.class);

    }



    //    @TaskAction
    public void createFile(Project target) {
        File buildDir = target.getBuildDir();
        File propertiesFile = new File(buildDir, "version.properties");
        try {
            propertiesFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();
        properties.setProperty("src.hash", hash(target));
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


        if (extension.isCompileIntoJar()) {
            String directory = extension.getJarDirectory();

            File file = new File(target.getBuildDir().getAbsolutePath() + File.separator + "resources" + File.separator + "main" + File.separator + directory);
            if (!file.exists()) file.mkdirs();
            File version = new File(file, "version.properties");
            try {
                fileWriter = new FileWriter(version);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fileWriter == null) {
                return;
            }
            try {
                System.out.println("getProject().getBuildDir().getAbsolutePath() = " + target.getBuildDir().getAbsolutePath());
                properties.store(fileWriter, null);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public String hash(Project project) {
        StringBuilder builder = new StringBuilder();
        project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets().forEach(sourceSet -> {
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
