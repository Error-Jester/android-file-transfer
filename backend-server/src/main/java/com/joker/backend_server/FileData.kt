package com.joker.backend_server

import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import java.io.File

fun getFiles(rootFile: File): ArrayList<File> {
    val filesList = rootFile.listFiles()

    // Obtaining File Objects
    val fileLists = ArrayList<File>()
    for(fileObject in filesList!!) {
        if(fileObject.isDirectory && !fileObject.isHidden) {
            fileLists.add(fileObject)
        }
    }
    for(fileObject in filesList) {
        val fileType = fileObject.name.lowercase()
        if(
            fileType.endsWith(".jpeg")||
            fileType.endsWith(".jpg")||
            fileType.endsWith(".png")||
            fileType.endsWith(".mp4")||
            fileType.endsWith(".mp3")||
            fileType.endsWith(".pdf")||
            fileType.endsWith(".epub")||
            fileType.endsWith(".mobi")||
            fileType.endsWith(".html")||
            fileType.endsWith(".js")
        ) {
            fileLists.add(fileObject)
        }
    }
    return fileLists
}