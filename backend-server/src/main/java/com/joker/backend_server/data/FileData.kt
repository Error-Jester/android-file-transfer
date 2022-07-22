package com.joker.backend_server

import android.os.Environment
import android.util.Log
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import java.io.File
import java.lang.Exception

/// obtains files and their paths
fun getFiles(rootFile: File = Environment.getExternalStorageDirectory()): MutableList<File> {
    try {
        val filesList = rootFile.listFiles()!!
        val receiverList = mutableListOf<File>()
        for(file in filesList) {
            if(file.isDirectory){
                receiverList.add(file)
                continue
            }
            if(file.name[0] != '.'){
                receiverList.add(file)
            }
        }
        return receiverList
    } catch (e: Exception) {
        val filesList = System.getenv("EXTERNAL_STORAGE")?.let { File(it).listFiles() }!!
        val receiverList = mutableListOf<File>()
        for(file in filesList) {
            if(file.isDirectory){
                receiverList.add(file)
                continue
            }
            if(!file.name.contains(".")){
                receiverList.add(file)
            }
        }
        return receiverList
    }
}