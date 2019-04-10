package com.nstu.technician.domain.model.tool

import com.nstu.technician.domain.model.Entity
import com.nstu.technician.domain.model.tool.Implements

class ImplementUnit(
    oid: Int,
    val code: String,
    val impl: Implements
) : Entity(oid)