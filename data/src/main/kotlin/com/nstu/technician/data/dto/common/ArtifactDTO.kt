package com.nstu.technician.data.dto.common

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.domain.model.FileNameExt
import com.nstu.technician.domain.model.common.OwnDateTime
import java.text.SimpleDateFormat
import java.util.*

data class ArtifactDTO(
    override val oid: Long,
    val type: Int,
    val name: String,
    val original: FileNameExt,
    val date: OwnDateTime,
    val fileSize: Long
) : EntityDTO(oid) {

    fun systemFileName(): String {
        val datePart = SimpleDateFormat("yyyyMMdd_hhmmss", Locale.ROOT).format(Date(date.timeInMS))
        return "${datePart}_$oid(${original.getFileNameExt()}).${original.ext}"
    }
}