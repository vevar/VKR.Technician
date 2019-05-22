package com.nstu.technician.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FileNameExt(
    val name: String,
    val path: String,
    val ext: String
) : java.io.Serializable {

    fun getFileNameExt() = "$name.$ext"
}