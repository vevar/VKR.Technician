package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.common.GPSPointDTO
import com.nstu.technician.domain.model.common.OwnDateTime

data class ShiftDTO(
    override val oid: Long,
    val date: OwnDateTime,
    val points: List<EntityLink<GPSPointDTO>>? = null,
    val visits: List<EntityLink<MaintenanceDTO>>? = null
): EntityDTO(oid)