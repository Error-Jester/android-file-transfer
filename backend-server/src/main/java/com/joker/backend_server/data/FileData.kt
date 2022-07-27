package com.joker.backend_server.data

import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import com.joker.backend_server.core.ServerException
import java.io.File
import java.lang.Exception

/// obtains files and their paths

fun getFiles(rootFile: File = File(System.getenv("EXTERNAL_STORAGE")!!)): MutableList<File> {
    try {
        val receiverList = mutableListOf<File>(Environment.getExternalStorageDirectory())
        rootFile.listFiles()!!.forEach { receiverList.add(it) }
        Log.d("FILES", receiverList[0].path)
        return receiverList

    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("GETFILES", e.message!!)
        throw ServerException()
    }
}

@RequiresApi(Build.VERSION_CODES.R)
fun getA11Files(rootFile: File = Environment.getExternalStorageDirectory()): MutableList<File> {
    try {
        val receiverList = mutableListOf<File>()
        rootFile.listFiles()!!.forEach { receiverList.add(it) }
        return receiverList

    }  catch (e: Exception) {
        throw ServerException()
    }
}