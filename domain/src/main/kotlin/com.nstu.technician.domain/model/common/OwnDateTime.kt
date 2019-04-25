package com.nstu.technician.domain.model.common

import kotlinx.serialization.Serializable

@Serializable
data class OwnDateTime(
    val timeInMS: Long
)
