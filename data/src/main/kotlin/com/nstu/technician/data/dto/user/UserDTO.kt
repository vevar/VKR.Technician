package com.nstu.technician.data.dto.user

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink

data class UserDTO(
    override val oid: Long,
    val lastName: String,
    val firstName: String,
    val middleName: String,
    val sessionToken: String,
    val account: EntityLink<AccountDTO>?
):EntityDTO(oid)