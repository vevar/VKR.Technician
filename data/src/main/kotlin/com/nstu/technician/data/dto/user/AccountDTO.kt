package com.nstu.technician.data.dto.user

import com.nstu.technician.data.dto.EntityDTO

data class AccountDTO(
    override val oid: Long,
    var login: String,
    var password: String
):EntityDTO(oid)