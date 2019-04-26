package com.nstu.technician.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FileNameExt(
    val fileName: String,
    val path: String,
    val ext: String
):java.io.Serializable