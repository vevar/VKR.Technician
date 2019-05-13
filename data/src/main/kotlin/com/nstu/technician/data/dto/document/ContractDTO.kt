package com.nstu.technician.data.dto.document

import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.domain.model.common.OwnDateTime

data class ContractDTO(
    @Transient
    override val oid: Long,
    override val docType: Int,
    override val number: String,
    override val date: OwnDateTime,
    override val docscan: EntityLink<ArtifactDTO>,
    override val state: Int
) : DocumentDTO(oid = oid, docType = docType, number = number, date = date, docscan = docscan, state = state) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContractDTO

        if (oid != other.oid) return false
        if (docType != other.docType) return false
        if (number != other.number) return false
        if (date != other.date) return false
        if (docscan != other.docscan) return false
        if (state != other.state) return false

        return true
    }

    override fun hashCode(): Int {
        var result = oid.hashCode()
        result = 31 * result + docType
        result = 31 * result + number.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + docscan.hashCode()
        result = 31 * result + state
        return result
    }

    override fun toString(): String {
        return "ContractDTO(oid=$oid, docType=$docType, number='$number', date=$date, docscan=$docscan, forChangeState=$state)"
    }


}

