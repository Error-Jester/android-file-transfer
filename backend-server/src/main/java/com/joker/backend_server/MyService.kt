package com.joker.backend_server

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.joker.backend_server.plugins.callLogging
import com.joker.backend_server.plugins.configureHTTP
import com.joker.backend_server.plugins.serialization
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyService : Service(){
    companion object {
        const val port = 5001
        var ip_address = NetworkUtils.getLocalIpAddress()!!
        var serverLink = "$ip_address:$port"
    }

    var onRunning = true
    private val server by lazy {
            embeddedServer(Netty, port, watchPaths = emptyList()) {
                configureHTTP()
                serialization()
                callLogging()
                Log.d("EMBEDDED SERVER", "started")
            }
        }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            server.start(wait = true)
            intent!!.putExtra("running", onRunning)
        }
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
        return LocalBinder()
    }

    override fun onDestroy() {
        server.stop()
        Log.d("MyService", "onDestroy")
        super.onDestroy()
    }
}