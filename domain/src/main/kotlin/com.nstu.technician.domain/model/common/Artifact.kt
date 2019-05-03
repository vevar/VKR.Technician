package com.nstu.technician.domain.model.common

import com.nstu.technician.domain.model.FileNameExt
import kotlinx.serialization.Serializable

@Serializable
data class Artifact(
    val oid: Long,
    val type: Int,
    val name: String,
    val original: FileNameExt,
    val date: OwnDateTime,
    val fileSize: Long
) : java.io.Serializable