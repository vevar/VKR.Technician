package com.nstu.technician.data.dto

import com.nstu.technician.data.dto.common.AddressDTO
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.data.dto.common.GPSPointDTO
import com.nstu.technician.data.dto.document.ContractDTO
import com.nstu.technician.data.dto.document.ContractorDTO
import com.nstu.technician.data.dto.job.*
import com.nstu.technician.data.dto.tool.ImplementUnitDTO
import com.nstu.technician.data.dto.tool.ImplementsDTO
import com.nstu.technician.domain.model.FileNameExt
import com.nstu.technician.domain.model.common.OwnDateTime

fun getMaintenanceDTO(oid: Long): MaintenanceDTO {

    return MaintenanceDTO(
        oid = oid,
        facility = EntityLink(getFacilityDTO(oid)),
        endTime = getOwnTime(),
        beginTime = getOwnTime(),
        duration = 30,
        maintenanceType = 2,
        state = 1,
        visitDate = getOwnTime(),
        jobList = getListSomeObject { EntityLink(getMaintenanceJobDTO(it)) },
        parent = null,
        voiceMassage = null,
        workCompletionReport = null
    )
}

fun getMaintenanceDTOWithShiftDTO(oid: Long, shiftDTO: ShiftDTO): MaintenanceDTO {
    val maintenanceDTO = getMaintenanceDTO(oid)
    maintenanceDTO.shift = EntityLink(shiftDTO)
    return maintenanceDTO
}

private fun getShiftDTO_STUB(oid: Long): ShiftDTO {
    return ShiftDTO(
        oid = oid,
        date = getOwnTime()
    )
}

fun getShiftDTO(oid: Long): ShiftDTO {
    return ShiftDTO(
        oid = oid,
        date = getOwnTime(),
        points = getListSomeObject { EntityLink(getGPSPointDTO(it)) },
        visits = getListSomeObject { EntityLink(getMaintenanceDTOWithShiftDTO(it, getShiftDTO_STUB(oid))) }
    )
}

fun getOwnTime(): OwnDateTime {
    return OwnDateTime(1554713066603)
}

fun getFacilityDTO(oid: Long): FacilityDTO {
    return FacilityDTO(
        oid = oid,
        name = "Пушка",
        address = getAddressDTO(378),
        assingmentDate = getOwnTime()
    )
}

fun getFacilityDTOWithContractDTO(oid: Long): FacilityDTO {
    val facilityDTO = getFacilityDTO(oid)
    val contractDTO = getContractDTO(372)

    facilityDTO.contract = EntityLink(contractDTO)
    contractDTO.facility = EntityLink(facilityDTO)

    return facilityDTO
}

fun getAddressDTO(oid: Long): AddressDTO {
    return AddressDTO(
        street = "Пупкина", office = "11", home = "2",
        location = getGPSPointDTO(62)
    )
}

fun getGPSPointDTO(oid: Long): GPSPointDTO {
    return GPSPointDTO(oid, 54.04, 54.05)
}

fun <T> getListSomeObject(function: (oid: Long) -> T): List<T> {
    val list = mutableListOf<T>()
    for (i in 0L..5L) {
        val elem = function.invoke(i)
        list.add(elem)
    }
    return list
}

fun getMaintenanceJobDTO(oid: Long): MaintenanceJobDTO {
    return MaintenanceJobDTO(
        oid = oid,
        jobState = 1,
        jobType = EntityLink(getJobTypeDTO()),
        duration = 12,
        beginTime = getOwnTime(),
        endTime = getOwnTime(),
        implList = getListSomeObject { EntityLink(getImplementsDTO(it)) },
        beginPhoto = null,
        endPhoto = null,
        components = null,
        problem = null
    )
}

fun getImplementsDTO(oid: Long): ImplementsDTO {
    return ImplementsDTO(
        oid = oid,
        name = "Hammer"
    )
}

fun getImplementUnitDTO(oid: Long): ImplementUnitDTO {
    return ImplementUnitDTO(
        oid = oid,
        code = "297",
        impl = EntityLink(getImplementsDTO(65))
    )
}

fun getJobTypeDTO(): JobTypeDTO {
    return JobTypeDTO(
        oid = 12,
        duration = 894,
        name = "job type name",
        description = "@description",
        impList = getListSomeObject { EntityLink(getImplementsDTO(it)) }
    )
}

fun getArtifactDTO(oid: Long, type: Int): ArtifactDTO {
    return ArtifactDTO(
        oid = oid,
        date = getOwnTime(),
        fileSize = 234,
        name = "SomeFileName",
        original = FileNameExt("asd", "tyui", "jpg"),
        type = type
    )
}

fun getContractDTO(oid: Long): ContractDTO {
    return ContractDTO(
        oid = oid,
        artifact = EntityLink(getArtifactDTO(32, 1)),
        contractor = EntityLink(getContractorDTO(852)),
        date = getOwnTime(),
        docType = 1,
        number = "123",
        state = 1
    )
}

fun getContractDTOWithFacilityDTO(oid: Long): ContractDTO {
    val contractDTO = getContractDTO(oid)

    val facilityDTO = getFacilityDTO(oid)

    facilityDTO.contract = EntityLink(contractDTO)
    contractDTO.facility = EntityLink(facilityDTO)

    return contractDTO
}

fun getContractorDTO(oid: Long): ContractorDTO {
    return ContractorDTO(
        oid = oid,
        address = getAddressDTO(5364),
        INN = "8793412",
        name = "gaga"
    )
}