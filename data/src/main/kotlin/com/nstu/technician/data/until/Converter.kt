package com.nstu.technician.data.until

import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.document.DocumentDTO
import com.nstu.technician.data.dto.job.FacilityDTO
import com.nstu.technician.data.dto.job.MaintenanceDTO
import com.nstu.technician.data.dto.job.MaintenanceJobDTO
import com.nstu.technician.data.dto.job.ShiftDTO
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.TechnicianDTO
import com.nstu.technician.data.dto.user.UserDTO
import com.nstu.technician.domain.model.Shift
import com.nstu.technician.domain.model.document.Document
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
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
        user.account = convertToModel(
            userDTO.account.ref ?: throw IllegalStateException("Account must be set")
        )
    }

    return user
}

fun convertToDTO(technician: Technician): TechnicianDTO {
    return TechnicianDTO(
        technician.oid,
        EntityLink(technician.user.oid, convertToDTO(technician.user))
    )
}


fun convertToModel(technicianDTO: TechnicianDTO): Technician {
    return Technician(
        technicianDTO.oid,
        convertToModel(technicianDTO.user.ref ?: throw IllegalStateException("User must be set"))
    )
}

fun convertToDTO(shift: Shift): ShiftDTO {
    return ShiftDTO(
        shift.oid,
        shift.date,
        shift.visits?.map { EntityLink(it.oid, convertToDTO(it)) },
        shift.points?.map { EntityLink(it.oid, it) }
    )
}

fun convertToModel(shiftDTO: ShiftDTO): Shift {
    val shift = Shift(shiftDTO.oid, shiftDTO.date)
    val points = shiftDTO.points
    val visits = shiftDTO.visits

    if (!points.isNullOrEmpty()) {
        shift.points = points.filter { it.ref != null }.map { it.ref!! }
    }
    if (!visits.isNullOrEmpty()) {
        shift.visits = visits.filter { it.ref != null }.map { convertToModel(it.ref!!) }
    }
    return shift
}

fun convertToModel(maintenanceDTO: MaintenanceDTO): Maintenance {
    val maintenance = Maintenance(
        maintenanceDTO.oid,
        convertToModel(maintenanceDTO.facility.ref ?: throw IllegalStateException()),
        maintenanceDTO.visitDate,
        maintenanceDTO.duration,
        Maintenance.Type.values()[maintenanceDTO.maintenanceType],
        Maintenance.State.values()[maintenanceDTO.state]
    )
    maintenance.beginTime = maintenanceDTO.beginTime
    maintenance.endTime= maintenanceDTO.endTime
    maintenance.jobList= maintenanceDTO.jobList?.filter { it.ref!=null }?.map { convertToModel(it.ref!!) }

    return maintenance
}

fun convertToModel(documentDTO: DocumentDTO): Document {
    TODO()
}

fun convertToModel(maintenanceJobDTO: MaintenanceJobDTO): MaintenanceJob {
    TODO()
}

fun convertToDTO(maintenance: Maintenance): MaintenanceDTO {
    return MaintenanceDTO(
        maintenance.oid,
        EntityLink(maintenance.facility.oid, convertToDTO(maintenance.facility)),
        maintenance.visitDate,
        maintenance.duration,
        maintenance.maintenanceType.ordinal,
        maintenance.state.ordinal
    )
}

fun convertToModel(facilityDTO: FacilityDTO): Facility {
    return Facility(
        facilityDTO.oid,
        facilityDTO.name,
        facilityDTO.address,
        facilityDTO.assingmentDate
    )
}

fun convertToDTO(facility: Facility): FacilityDTO {
    return FacilityDTO(
        facility.oid,
        facility.name,
        facility.address,
        facility.assingmentDate
    )
}
