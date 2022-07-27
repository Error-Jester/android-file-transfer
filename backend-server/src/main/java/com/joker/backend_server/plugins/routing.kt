package com.joker.backend_server.plugins

import android.os.Environment
import android.util.Log
import com.joker.backend_server.core.ServerException
import com.joker.backend_server.data.getFiles
import com.joker.backend_server.models.FileHandler
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.partialcontent.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File



fun Application.configureHTTP() {
    install(PartialContent)
    install(AutoHeadResponse)

    routing {
        // GET a list of all files
        getAllFiles("/{path...}")

        // Static Files
        static("/static"){
            resources("static") 
        }
        


        // download File
        get("/download/{name...}") {
            // get filename from request url
            var filename = ""
            call.parameters.getAll("name")!!.forEach { filename = "$filename/$it" }
            Log.d("CALL", filename)
            // construct reference to file
            // ideally this would use a different filename
            val file = File(filename)
            if(file.exists()) {
                call.respondFile(file)
            }
            else call.respond(HttpStatusCode.NotFound)
        }

        var fileDescription = ""
        var fileName = ""

        // Upload
        post("/upload") {
            val multipartData = call.receiveMultipart()

            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        fileDescription = part.value
                    }
                    is PartData.FileItem -> {
                        fileName = part.originalFileName as String
                        val fileBytes = part.streamProvider().readBytes()
                        if(File("${Environment.DIRECTORY_DOWNLOADS}/upload").mkdir())
                            File("${Environment.DIRECTORY_DOWNLOADS}/upload/$fileName").writeBytes(fileBytes)
                        else
                            throw ServerException()
                    }
                    else -> {}
                }
            }

            call.respondText("$fileDescription is uploaded to 'uploads/$fileName'")
        }
    }
}


fun Routing.getAllFiles(path: String = "sdcard") {
    get(path) {
        var filePath = ""
        val id = call.parameters.getAll("path")
        if(id != null) id.forEach { filePath = "$filePath/$it" } else filePath = ""

        // Response Status
        call.response.status(HttpStatusCode.Accepted)
        // Headers
        call.response.header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        call.response.header(HttpHeaders.Connection, "keep-alive")

        // response
        call.response.status(HttpStatusCode.Accepted)
        val fileFocus = if(filePath != "") File(filePath) else Environment.getExternalStorageDirectory()!!
        val apiFiles = getFiles(fileFocus).map {
            FileHandler(
                name = it.name,
                sizeInKB = (it.length() / 1024),
                path = it.absolutePath,
                isDir = it.isDirectory
            )
        }.toMutableList()
        Log.d("API", apiFiles[1].path)

        call.respond(apiFiles)
    }
}






