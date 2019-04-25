package com.nstu.technician.data.dto.tool

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.until.isNotEqualSafeList

data class ImplementsDTO(
    override val oid: Long,
    val name: String,
    val units: List<EntityLink<ImplementUnitDTO>>? = null
) : EntityDTO(oid){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImplementsDTO

        if (oid != other.oid) return false
        if (name != other.name) return false
        if (isNotEqualSafeList(units,other.units)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = oid.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (units?.hashCode() ?: 0)
        return result
    }
}
