//package com.joker.androidfiletransfer.data
//
//import android.util.Log
//import com.joker.androidfiletransfer.model.FileRetrieved
//import io.ktor.client.*
//import io.ktor.client.call.*
//import io.ktor.client.engine.android.*
//import io.ktor.client.plugins.*
//import io.ktor.client.plugins.kotlinx.serializer.*
//import io.ktor.client.plugins.logging.*
//import io.ktor.client.plugins.json.JsonPlugin
//import io.ktor.client.plugins.observer.*
//import io.ktor.client.request.*
//import io.ktor.http.*
//import kotlinx.serialization.serializer
//
//object ApiRoutes {
//    lateinit var BASE_URL: String
//    const val DOWNLOAD = "download/"
//    const val UPLOAD = "upload/"
//}
//
//
//
//interface FileGet {
//
//    suspend fun getDirectoryFiles(): List<FileRetrieved>
//    suspend fun downloadFile()
//    suspend fun uploadFile()
//
//    companion object {
//        private const val TIME_OUT = 60_000
//        fun create(): FileGet {
//            return FileTransfer(client = HttpClient(Android) {
//                install(Logging) {
//                    level = LogLevel.ALL
//                }
//                install(JsonPlugin) {
//                    serializer = KotlinxSerializer()
//                }
//                engine {
//                    connectTimeout = TIME_OUT
//                    socketTimeout = TIME_OUT
//                }
//                install(ResponseObserver) {
//                    onResponse {
//                        res ->
//                            Log.d("HTTP Status", "${res.status.value}")
//                    }
//                }
//                install(DefaultRequest) {
//                    header(HttpHeaders.ContentType, ContentType.Application.Json)
//                }
//            })
//        }
//    }
//}
//
//class FileTransfer(val client: HttpClient) : FileGet {
//    override suspend fun getDirectoryFiles(): List<FileRetrieved> = client.get(urlString = ApiRoutes.BASE_URL).body()
//
//    }
//
//    override suspend fun downloadFile() {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun uploadFile() {
//        TODO("Not yet implemented")
//    }
//
//
//}