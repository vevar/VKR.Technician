package com.nstu.technician.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val oid: Long,
    var login: String,
    var password: String
)