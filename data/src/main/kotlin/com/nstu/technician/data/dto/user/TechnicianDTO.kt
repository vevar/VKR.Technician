package com.nstu.technician.data.dto.user

import com.nstu.technician.data.dto.EntityLink

data class TechnicianDTO(
    val oid: Long,
    val user: EntityLink<UserDTO>
)