package com.nstu.technician.data.database.entity.common

import androidx.room.Embedded
import androidx.room.Entity
import com.nstu.technician.domain.model.FileNameExt
import com.nstu.technician.domain.model.common.OwnDateTime

@Entity
data class ArtifactEntity(
    val oid: Long,
    val type: Int,
    val name: String,
    @Embedded val original: FileNameExt,
    @Embedded val date: OwnDateTime,
    val fileSize: Long
)