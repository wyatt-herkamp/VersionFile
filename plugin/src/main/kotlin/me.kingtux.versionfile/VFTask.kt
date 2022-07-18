package me.kingtux.versionfile

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.function.Consumer

class VFTask(project: Project) {
    private val extension: VFExtension

    init {
        extension = project.extensions.create("versionFileConfig", VFExtension::class.java)
    }

    fun createFile(target: Project) {
        val buildDir = target.buildDir
        val propertiesFile = File(buildDir, "version.properties")
        try {
            propertiesFile.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val properties = Properties()
        properties.setProperty("src.hash", hash(target))
        val git = File(".git")
        if (git.exists()) {
            //Use Git code :)
            try {
                properties.setProperty("git.commit.hash", execCmd("git rev-parse HEAD"))
                properties.setProperty("git.commit.branch", execCmd("git rev-parse --abbrev-ref HEAD"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        properties.setProperty("build.time", System.currentTimeMillis().toString())
        properties.setProperty("version", target.version.toString())
        var fileWriter: FileWriter? = null
        try {
            fileWriter = FileWriter(propertiesFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (fileWriter == null) {
            return
        }
        try {
            properties.store(fileWriter, null)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (extension.isCompileIntoJar) {
            val directory = extension.jarDirectory
            val file =
                File(target.buildDir.absolutePath + File.separator + "resources" + File.separator + "main" + File.separator + directory)
            if (!file.exists()) file.mkdirs()
            val version = File(file, "version.properties")
            val fileWriter: FileWriter? = try {
                FileWriter(version)
            } catch (e: IOException) {
                e.printStackTrace(); null
            }
            try {
                properties.store(fileWriter, null)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun hash(project: Project): String? {
        val builder = StringBuilder()
        val messageDigest: MessageDigest = try {
            MessageDigest.getInstance("SHA-256")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return null
        }
        project.convention.getPlugin(JavaPluginConvention::class.java).sourceSets.forEach(Consumer { sourceSet: SourceSet ->
            sourceSet.allSource.forEach(Consumer { file: File ->
                builder.append(getFileChecksum(messageDigest, file))
            })
        })
        val s = builder.toString()
        println("s = $s")
        messageDigest.update(s.toByteArray())
        val digest = messageDigest.digest()
        val sb = StringBuilder()
        for (i in digest.indices) {
            sb.append(((digest[i].toInt() and 0xff) + 0x100).toString(16).substring(1))
        }
        return sb.toString()
    }

    companion object {
        private fun getFileChecksum(digest: MessageDigest, file: File): String {
            val fis = FileInputStream(file)
            val byteArray = ByteArray(1024)
            var bytesCount: Int
            while (fis.read(byteArray).also { bytesCount = it } != -1) {
                digest.update(byteArray, 0, bytesCount)
            }
            fis.close()
            val bytes = digest.digest()
            val sb = StringBuilder()
            for (i in bytes.indices) {
                sb.append(((bytes[i].toInt() and 0xff) + 0x100).toString(16).substring(1))
            }
            return sb.toString()
        }

        @Throws(IOException::class)
        fun execCmd(cmd: String?): String {
            val s = Scanner(Runtime.getRuntime().exec(cmd).inputStream).useDelimiter("\\A")
            return if (s.hasNext()) s.next() else ""
        }
    }
}