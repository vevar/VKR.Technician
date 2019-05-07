package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.common.AddressDTO
import com.nstu.technician.data.dto.document.ContractDTO
import com.nstu.technician.domain.model.common.OwnDateTime


data class FacilityDTO(
    override val oid: Long,
    val identifier: String,
    val name: String,
    val address: AddressDTO,
    val assingmentDate: OwnDateTime,
    var contract: EntityLink<ContractDTO>? = null
) : EntityDTO(oid) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FacilityDTO

        if (oid != other.oid) return false
        if (name != other.name) return false
        if (address != other.address) return false
        if (assingmentDate != other.assingmentDate) return false
        if (contract.hashCode() != other.contract.hashCode()) return false

        return true
    }

    override fun hashCode(): Int {
        var result = oid.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + assingmentDate.hashCode()

        return result
    }

    override fun toString(): String {
        return "FacilityDTO(oid=$oid, mName='$name', address=$address, assingmentDate=$assingmentDate, contract=${contract.hashCode()})"
    }
}