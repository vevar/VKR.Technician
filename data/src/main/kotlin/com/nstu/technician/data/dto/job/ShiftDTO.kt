package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.common.GPSPointDTO
import com.nstu.technician.data.until.isNotEqualSafeList
import com.nstu.technician.domain.model.common.OwnDateTime

data class ShiftDTO(
    override val oid: Long,
    val date: OwnDateTime,
    val points: List<EntityLink<GPSPointDTO>>? = null,
    val visits: List<EntityLink<MaintenanceDTO>>? = null
) : EntityDTO(oid) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShiftDTO

        if (oid != other.oid) return false
        if (date != other.date) return false
        if (isNotEqualSafeList(points, other.points)) return false
        if (isNotEqualSafeList(visits, other.visits)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = oid.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + (points?.hashCode() ?: 0)
        result = 31 * result + (visits?.hashCode() ?: 0)
        return result
    }
}