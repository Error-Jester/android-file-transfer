package com.joker.backend_server.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.serialization() {
    install(ContentNegotiation) {
        json()
    }
}