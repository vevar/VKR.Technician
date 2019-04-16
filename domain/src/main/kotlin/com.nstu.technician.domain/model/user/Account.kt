package com.nstu.technician.domain.model.user

data class Account(
    val oid: Long,
    var login: String,
    var password: String
)