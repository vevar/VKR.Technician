package com.nstu.technician.data.until

import com.nstu.technician.data.database.entity.ShiftEntity
import com.nstu.technician.data.database.entity.common.GPSEntity
import com.nstu.technician.data.database.entity.job.FacilityEntity
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.common.AddressDTO
import com.nstu.technician.data.dto.document.ContractDTO
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
import com.nstu.technician.domain.model.document.Contract
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
        points = shiftDTO.points?.filter { it.ref != null }?.map { it.ref!! }
    )
}

fun convertToModel(maintenanceDTO: MaintenanceDTO): Maintenance {
    val maintenance = Maintenance(
        maintenanceDTO.oid,
        convertToModel(maintenanceDTO.facility.ref ?: throw IllegalStateException()),
        maintenanceDTO.visitDate,
        maintenanceDTO.duration,
        maintenanceDTO.maintenanceType,
        maintenanceDTO.state
    )
    maintenance.beginTime = maintenanceDTO.beginTime
    maintenance.endTime = maintenanceDTO.endTime
    maintenance.jobList = maintenanceDTO.jobList?.filter { it.ref != null }?.map { convertToModel(it.ref!!) }

    return maintenance
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
    facility.contract = facilityDTO.contract?.let { linkContractDTO ->
        linkContractDTO.ref?.let { contractDTO ->
            convertToModel(contractDTO)
        }
    }

    return facility
}

fun convertToModel(contractDTO: ContractDTO): Contract {
    return Contract(
        contractDTO.oid,
        contractDTO.name,
        contractDTO.INN,
        contractDTO.address,
        convertToModel(contractDTO.contractor.ref ?: throw IllegalStateException("contractor must be set")),
        contractDTO.docType,
        contractDTO.number,
        contractDTO.date,
        contractDTO.artifact.ref ?: throw IllegalStateException("artifact must be set")
    )
}

fun convertToModel(contractorDTO: ContractorDTO): Contractor {
    return Contractor(
        contractorDTO.oid,
        contractorDTO.name,
        contractorDTO.address,
        contractorDTO.INN
    )
}

fun ShiftDTO.convertToShiftEntity(): ShiftEntity {
    return ShiftEntity(
        oid, date
    )
}

fun AddressDTO.convertToGpsEntity(): GPSEntity {
    return GPSEntity(
        oid = location.oid,
        office = office,
        home = home,
        street = street,
        latitude = location.geox,
        longitude = location.geoy
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
        street, home, location, office
    )
}

fun FacilityEntity.convertToFacilityDTO(gpsEntity: GPSEntity): FacilityDTO {
    return FacilityDTO(
        oid = oid,
        name = name,
        address = gpsEntity.convertToAddressDTO(),
        assingmentDate = assingmentDate
    )
}

fun GPSEntity.convertToAddressDTO(): AddressDTO {
    return AddressDTO(
        street = street ?: throw NullPointerException("street must be set"),
        home = home ?: throw NullPointerException("home must be set"),
        location = GPSPoint(oid, latitude, longitude),
        office = office
    )
}
