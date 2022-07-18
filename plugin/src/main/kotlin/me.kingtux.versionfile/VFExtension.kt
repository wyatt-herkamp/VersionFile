package me.kingtux.versionfile

class VFExtension {
    var jarDirectory = ""
    var dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    var isCompileIntoJar = false
    var customValues: Map<String, String> = HashMap()

    constructor() {}
    constructor(dateFormat: String, compileIntoJar: Boolean) {
        this.dateFormat = dateFormat
        isCompileIntoJar = compileIntoJar
    }
}