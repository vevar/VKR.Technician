package com.nstu.technician.data.until

import com.nstu.technician.data.database.entity.ShiftEntity
import com.nstu.technician.data.database.entity.common.AddressEntity
import com.nstu.technician.data.database.entity.common.GPSEntity
import com.nstu.technician.data.database.entity.job.FacilityEntity
import com.nstu.technician.data.database.entity.job.MaintenanceEntity
import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.common.AddressDTO
import com.nstu.technician.data.dto.common.GPSPointDTO
import com.nstu.technician.data.dto.document.ContractorDTO
import com.nstu.technician.data.dto.job.*
import com.nstu.technician.data.dto.tool.ImplementUnitDTO
import com.nstu.technician.data.dto.tool.ImplementsDTO
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.TechnicianDTO
import com.nstu.technician.data.dto.user.UserDTO
import com.nstu.technician.domain.model.Shift
import com.nstu.technician.domain.model.common.Address
import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.document.Contractor
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.model.facility.JobType
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.domain.model.tool.ImplementUnit
import com.nstu.technician.domain.model.tool.Implements
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User

fun convertToDTO(account: Account): AccountDTO {
    return AccountDTO(account.oid, account.login, account.password)
}


fun convertToModel(account: AccountDTO): Account {
    return Account(account.oid, account.login, account.password)
}

fun convertToDTO(user: User): UserDTO {
    return UserDTO(
        user.oid,
        user.lastName,
        user.firstName,
        user.middleName,
        user.sessionToken,
        user.account?.let {
            EntityLink(it.oid, convertToDTO(it))
        } ?: throw IllegalStateException("Account must be set")
    )
}

fun convertToModel(userDTO: UserDTO): User {
    val user = User(
        userDTO.oid,
        userDTO.lastName,
        userDTO.firstName,
        userDTO.middleName,
        userDTO.sessionToken
    )
    if (userDTO.account.ref != null) {
        user.account = convertToModel(userDTO.account.ref ?: throw IllegalStateException("Account must be set"))
    }

    return user
}

fun convertToModel(technicianDTO: TechnicianDTO): Technician {
    return Technician(
        technicianDTO.oid,
        convertToModel(technicianDTO.user.ref ?: throw IllegalStateException("User must be set"))
    )
}

fun convertToModel(shiftDTO: ShiftDTO): Shift {
    return Shift(
        shiftDTO.oid,
        shiftDTO.date,
        visits = shiftDTO.visits?.filter { it.ref != null }?.map { convertToModel(it.ref!!) },
        points = shiftDTO.points?.filter { it.ref != null }?.map { it.ref?.convertToGPSPoint()!! }
    )
}

fun convertToModel(maintenanceDTO: MaintenanceDTO): Maintenance {

    return Maintenance(
        maintenanceDTO.oid,
        convertToModel(maintenanceDTO.facility.ref ?: throw IllegalStateException()),
        maintenanceDTO.visitDate,
        maintenanceDTO.duration,
        maintenanceDTO.maintenanceType,
        maintenanceDTO.state,
        beginTime = maintenanceDTO.beginTime,
        endTime = maintenanceDTO.endTime,
        jobList = maintenanceDTO.jobList?.filter { it.ref != null }?.map { convertToModel(it.ref!!) }
    )
}


fun convertToModel(maintenanceJobDTO: MaintenanceJobDTO): MaintenanceJob {
    return MaintenanceJob(
        maintenanceJobDTO.oid,
        maintenanceJobDTO.jobState,
        convertToModel(maintenanceJobDTO.jobType.ref ?: throw IllegalStateException("jobType must be set"))
    )
}

fun convertToModel(jobTypeDTO: JobTypeDTO): JobType {
    val jobType = JobType(
        jobTypeDTO.oid,
        jobTypeDTO.name,
        jobTypeDTO.description,
        jobTypeDTO.duration,
        jobTypeDTO.impList?.map { implementsDTO ->
            implementsDTO.ref?.convertToImplement() ?: throw IllegalStateException("implementsDTO must be set")
        }
    )
    return jobType
}

fun convertToModel(facilityDTO: FacilityDTO): Facility {
    val facility = Facility(
        facilityDTO.oid,
        facilityDTO.name,
        facilityDTO.address.convertToAddress(),
        facilityDTO.assingmentDate
    )

    return facility
}

fun convertToModel(contractorDTO: ContractorDTO): Contractor {
    return Contractor(
        contractorDTO.oid,
        contractorDTO.name,
        contractorDTO.address,
        contractorDTO.INN
    )
}


fun <F : EntityDTO, T> EntityLink<F>.convertToObject(function: (F) -> T): T {
    return ref?.let(function) ?: throw IllegalStateException("ref must be set")
}

fun ShiftDTO.convertToShiftEntity(): ShiftEntity {
    return ShiftEntity(
        oid, date
    )
}


fun MaintenanceDTO.convertToMaintenanceEntity(shiftId: Long): MaintenanceEntity {
    return MaintenanceEntity(
        oid = oid,
        beginTime = beginTime?.timeInMS,
        endTime = endTime?.timeInMS,
        facilityId = facility.oid,
        duration = duration,
        maintenanceType = maintenanceType,
        state = state,
        visitDate = visitDate.timeInMS,
        maintenanceParentId = parent?.oid,
        voiceMessageId = voiceMassage?.oid,
        workCompletionReportId = workCompletionReport?.oid,
        shiftId = shiftId
    )
}

fun AddressDTO.convertToAddressEntity(gpsPointId: Long): AddressEntity {
    return AddressEntity(
        oid = gpsPointId,
        home = home,
        office = office,
        street = street,
        gpsPointId = gpsPointId
    )
}


fun FacilityDTO.convertToFacilityEntity(): FacilityEntity {
    return FacilityEntity(
        oid = oid,
        name = name,
        addressId = address.location.oid,
        contractId = contract?.oid,
        assingmentDate = assingmentDate
    )
}

fun ImplementsDTO.convertToImplement(): Implements {
    return Implements(
        oid, name, units = units?.filter { it.ref != null }?.map { entityLink: EntityLink<ImplementUnitDTO> ->
            entityLink.ref?.convertToImplementUnit()!!
        }
    )
}

fun ImplementUnitDTO.convertToImplementUnit(): ImplementUnit {
    return ImplementUnit(
        oid, code
    )
}

fun AddressDTO.convertToAddress(): Address {
    return Address(
        street, home, location.convertToGPSPoint(), office
    )
}

fun GPSPointDTO.convertToGPSPoint(): GPSPoint {
    return GPSPoint(oid, geoy, geox)
}

fun GPSPointDTO.convertToGPSEntity(): GPSEntity {
    return GPSEntity(
        oid = oid,
        latitude = geoy,
        longitude = geox
    )
}

fun GPSEntity.convertToGpsPointDTO(): GPSPointDTO {
    return GPSPointDTO(
        oid = oid,
        geox = longitude,
        geoy = latitude
    )
}

fun MaintenanceDTO.convertToMaintenance(): Maintenance {
    return Maintenance(
        oid = oid,
        facility = facility.convertToObject { it.convertToFacility() },
        visitDate = visitDate,
        state = state,
        maintenanceType = maintenanceType,
        duration = duration,
        endTime = endTime,
        beginTime = beginTime
    )
}

fun FacilityDTO.convertToFacility(): Facility {
    return Facility(
        oid,
        name,
        address.convertToAddress(),
        assingmentDate
    )
}

fun ShiftEntity.convertToShiftDTO(): ShiftDTO {
    return ShiftDTO(
        oid = oid,
        date = date
    )
}

fun ShiftEntity.convertToShiftDTO(listMaintenanceEntities: List<MaintenanceDTO>): ShiftDTO {
    return ShiftDTO(
        oid = oid,
        date = date,
        visits = listMaintenanceEntities.map { EntityLink(oid, it) }
    )
}

fun MaintenanceEntity.convertToMaintenanceDTO(facilityDTO: FacilityDTO): MaintenanceDTO {
    return MaintenanceDTO(
        oid = oid,
        facility = EntityLink(facilityDTO.oid, facilityDTO),
        visitDate = OwnDateTime(visitDate),
        state = state,
        maintenanceType = maintenanceType,
        duration = duration,
        endTime = if (endTime != null) OwnDateTime(endTime) else null,
        beginTime = if (beginTime != null) OwnDateTime(beginTime) else null
    )
}

fun FacilityEntity.convertToFacilityDTO(addressDTO: AddressDTO): FacilityDTO {
    return FacilityDTO(
        oid = oid,
        name = name,
        address = addressDTO,
        assingmentDate = assingmentDate
    )
}

fun AddressEntity.convertToAddressDTO(gpsPointDTO: GPSPointDTO): AddressDTO {
    return AddressDTO(
        street = street,
        home = home,
        office = office,
        location = gpsPointDTO
    )
}

