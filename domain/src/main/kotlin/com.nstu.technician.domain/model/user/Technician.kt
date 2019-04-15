package com.nstu.technician.domain.model.user

import com.nstu.technician.domain.model.EntityLink

data class Technician(
    val oid: Int,
    var user: EntityLink<User>
)