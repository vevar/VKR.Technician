package com.nstu.technician.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class Technician(
    val oid: Long,
    var user: User
)