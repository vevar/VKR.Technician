package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.tool.ImplementsDTO
import com.nstu.technician.data.until.isNotEqualSafeList

data class JobTypeDTO(
    override val oid: Long,
    val name: String,
    val description: String,
    val duration: Int,
    val impList: List<EntityLink<ImplementsDTO>>
) : EntityDTO(oid) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JobTypeDTO

        if (oid != other.oid) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (duration != other.duration) return false
        if (isNotEqualSafeList(impList, other.impList)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = oid.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + duration
        result = 31 * result + impList.hashCode()
        return result
    }
}