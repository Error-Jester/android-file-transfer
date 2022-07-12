package com.joker.backend_server.models

import kotlinx.serialization.Serializable

@Serializable
data class FileHandler(val name: String, val sizeInKB: Long, val path: String, val isDir: Boolean)