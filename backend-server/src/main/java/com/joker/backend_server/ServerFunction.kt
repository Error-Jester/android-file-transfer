package com.joker.backend_server

import android.os.Build
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRoutes() {
    routing {
        get("/") {

        }
        get("static") {
            this@routing.resources("res/html")
        }
    }

}