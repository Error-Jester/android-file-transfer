@file:OptIn(UseHttp2Push::class)

package com.joker.backend_server

import android.util.Log
import android.widget.Toast
import com.joker.backend_server.models.FileHandler
import io.ktor.http.*
import io.ktor.network.sockets.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.partialcontent.PartialContent
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.serialization.builtins.serializer
import java.io.File

fun Application.installConfigs() {
    install(WebSockets)
    install(CallLogging)
    install(ContentNegotiation) {
        json()
    }
}


fun Application.configureRoutes() {
    install(PartialContent)
    install(AutoHeadResponse)
    routing {
        get("/") {
            call.response.status(HttpStatusCode.Accepted)
            call.response.header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            call.response.header(HttpHeaders.Connection, "keep-alive")

            val files = getFiles()
            val apiFiles = mutableListOf<FileHandler>()
            for(file in files) {
                apiFiles.add(
                    FileHandler(
                        name = file.name,
                        sizeInKB = (file.length() / 1000),
                        path = file.absolutePath,
                        isDir = file.isDirectory
                )
                )
            }
            call.respond(apiFiles)
        }
        get(path = "{path...}"){
            var id = call.parameters.getAll("path")
            var path: String = ""
                id!!.map { path = "$path/$it" }
//            call.respondText("ID: $path")
            call.response.status(HttpStatusCode.Accepted)
            call.response.header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            call.response.header(HttpHeaders.Connection, "keep-alive")
            val files = getFiles(rootFile = File(path))
            val apiFiles = mutableListOf<FileHandler>()
            for (file in files) {
                apiFiles.add(
                    FileHandler(
                        name = file.name,
                        sizeInKB = (file.length() / 1000),
                        path = file.absolutePath,
                        isDir = file.isDirectory
                    )
                )
            }
            call.respond(apiFiles)

        }
//        transferingRoutes()
    }
}


//fun Routing.transferingRoutes() {
//    get("/download/{id}") {
//        call.response.status(HttpStatusCode.Created)
//        call.respondFile(fileGet){
//            call.response.header(HttpHeaders.ContentType, this.contentType.toString())
//            call.response.header(HttpHeaders.ContentLength, this.contentLength.toString())
//            call.response.header(HttpHeaders.ContentDisposition, ContentDisposition.Attachment
//                .withParameter(ContentDisposition.Parameters.FileName, fileGet.name)
//                .toString())
//
//        }
//    }
//    post("/upload") {
//        // retrieve all multipart data (suspending)
//        val multipart = call.receiveMultipart()
//        multipart.forEachPart { part ->
//            // if part is a file (could be form item)
//            if(part is PartData.FileItem) {
//                // retrieve file name of upload
//                val name = part.originalFileName!!
//                val file = File("/uploads/$name")
//
//                // use InputStream from part to save file
//                part.streamProvider().use { its ->
//                    // copy the stream to the file with buffering
//                    file.outputStream().buffered().use {
//                        // note that this is blocking
//                        its.copyTo(it)
//                    }
//                }
//            }
//            // make sure to dispose of the part after use to prevent leaks
//            part.dispose()
//        }
//    }
//
//}