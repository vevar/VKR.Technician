package com.nstu.technician.data.dto.document

import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.data.dto.job.FacilityDTO
import com.nstu.technician.domain.model.common.OwnDateTime

data class ContractDTO(
    override val oid: Long,
    override val docType: Int,
    override val number: String,
    override val date: OwnDateTime,
    override val artifact: EntityLink<ArtifactDTO>,
    override val state: Int,
    val contractor: EntityLink<ContractorDTO>,
    var facility: EntityLink<FacilityDTO>? = null
) : DocumentDTO(oid = oid, docType = docType, number = number, date = date, artifact = artifact, state = state) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContractDTO

        if (oid != other.oid) return false
        if (docType != other.docType) return false
        if (number != other.number) return false
        if (date != other.date) return false
        if (artifact != other.artifact) return false
        if (state != other.state) return false
        if (contractor != other.contractor) return false
        if (facility.hashCode() != other.facility.hashCode()) return false

        return true
    }

    override fun hashCode(): Int {
        var result = oid.hashCode()
        result = 31 * result + docType
        result = 31 * result + number.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + artifact.hashCode()
        result = 31 * result + state
        result = 31 * result + contractor.hashCode()
        return result
    }

    override fun toString(): String {
        return "ContractDTO(oid=$oid, docType=$docType, number='$number', date=$date, artifact=$artifact, state=$state, contractor=$contractor, facility=${facility.hashCode()})"
    }


}

