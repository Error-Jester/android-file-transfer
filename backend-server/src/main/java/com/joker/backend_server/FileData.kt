package com.joker.backend_server

import android.util.Log
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import java.io.File
import java.lang.Exception

fun getFiles(rootFile: File = File(System.getenv("EXTERNAL_STORAGE")!!)): List<File> {
    try {
        val filesList = rootFile.listFiles()
        // Obtaining File Objects
        val fileLists = ArrayList<File>()
        for (fileObject in filesList!!) {
            if (fileObject.isDirectory && !fileObject.isHidden) {
                fileLists.add(fileObject)
            }
        }
        for (fileObject in filesList) {
            val fileType = fileObject.name.lowercase()
            if (fileType[0] != '.') {
                fileLists.add(fileObject)
            }
        }
        return fileLists.toList()
    } catch (e: Exception) {
        val filesList = File(System.getenv("EXTERNAL_STORAGE")!!).listFiles()
        // Obtaining File Objects
        val fileLists = ArrayList<File>()
        for (fileObject in filesList!!) {
            if (fileObject.isDirectory && !fileObject.isHidden) {
                fileLists.add(fileObject)
            }
        }
        for (fileObject in filesList) {
            val fileType = fileObject.name.lowercase()
            if (fileType[0] != '.') {
                fileLists.add(fileObject)
            }
        }
        return fileLists.toList()
    }
}