package com.joker.backend_server

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.cors.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class MyService : Service() {
    companion object {
        private val PORT = 5001
        var path: String = System.getenv("EXTERNAL_STORAGE")!!
        var rootFile = File(path)
    }

    private val server by lazy {
            embeddedServer(Netty, PORT, watchPaths = emptyList()) {
                install(WebSockets)
                install(CallLogging)
                install(CORS)
                install(Compression) {
                    gzip()
                }
                configureRoutes()
            }
        }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            server.start(wait = true)
        }
        var reciever = getFiles(rootFile)
        intent!!.putExtra( "ip_addr" ,NetworkUtils.getLocalIpAddress())
        intent.putExtra("files", reciever)
        return START_STICKY
    }

    class LocalBinder : Binder() {
        val service: LocalBinder
            get() = this@LocalBinder
    }
    override fun onBind(intent: Intent): IBinder {
        CoroutineScope(Dispatchers.IO).launch {
            server.start(wait = true)
        }
        intent.putExtra( "ip_addr" ,NetworkUtils.getLocalIpAddress())
        return LocalBinder()
    }

    override fun onDestroy() {
        server.stop()
        super.onDestroy()
    }
}