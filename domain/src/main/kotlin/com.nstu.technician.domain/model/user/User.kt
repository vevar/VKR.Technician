package com.nstu.technician.domain.model.user

import com.nstu.technician.domain.model.EntityLink

data class User(
    val oid: Int,
    val sessionToken: String,
    val account: EntityLink<Account>
)