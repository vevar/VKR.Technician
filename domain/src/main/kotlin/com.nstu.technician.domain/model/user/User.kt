package com.nstu.technician.domain.model.user

data class User(
    val id: Int,
    val sessionToken: String,
    val account: Account
)