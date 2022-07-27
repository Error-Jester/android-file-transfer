package com.joker.androidfiletransfer.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileRetrieved(
    @SerialName("name")
    val name: String,

    @SerialName("sizeinKB")
    val sizeinKB: Long,

    @SerialName("path")
    val path: String,

    @SerialName("isDir")
    val isDir: Boolean)
