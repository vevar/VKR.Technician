package com.nstu.technician.domain.model.user

import com.nstu.technician.domain.model.EntityLink

data class User(
    val oid: Int,
    val lastName: String,
    val firstName: String,
    val middleName: String,
    var sessionToken: String,
    val account: EntityLink<Account?>
)