package com.nstu.technician.domain.model.user

data class Account(
    val oid: Int,
    val login: String,
    val password: String
)