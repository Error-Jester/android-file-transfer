package com.joker.backend_server.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.response.*
import io.netty.handler.logging.LogLevel
import org.slf4j.event.Level

fun Application.callLogging() {
    install(CallLogging) {
        this.level = Level.DEBUG
    }
}

