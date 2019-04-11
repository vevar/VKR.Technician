package com.nstu.technician.domain.model.user

import com.nstu.technician.domain.model.EntityLink

data class Technician(
    val oid: Int,
    val lastName: String,
    val firstName: String,
    val middleName: String
) {
    var account: EntityLink<Account>? = null
}