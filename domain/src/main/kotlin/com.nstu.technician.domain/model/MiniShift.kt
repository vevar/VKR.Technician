package com.nstu.technician.domain.model

import com.nstu.technician.domain.model.common.OwnDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MiniShift(
    val oid: Long,
    val date: OwnDateTime
) : java.io.Serializable