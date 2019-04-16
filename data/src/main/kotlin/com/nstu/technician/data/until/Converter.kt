package com.nstu.technician.data.until

import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.job.FacilityDTO
import com.nstu.technician.data.dto.job.MaintenanceDTO
import com.nstu.technician.data.dto.job.ShiftDTO
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.TechnicianDTO
import com.nstu.technician.data.dto.user.UserDTO
import com.nstu.technician.domain.model.Shift
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
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
        EntityLink(user.account.oid, convertToDTO(user.account))
    )

}


fun convertToModel(userDTO: UserDTO): User {
    return User(
        userDTO.oid,
        userDTO.lastName,
        userDTO.firstName,
        userDTO.middleName,
        userDTO.sessionToken,
        convertToModel(
            userDTO.account.ref ?: throw IllegalStateException("Account must be set")
        )
    )
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
    return Shift(
        shiftDTO.oid,
        shiftDTO.date
    )
}

fun convertToModel(maintenanceDTO: MaintenanceDTO): Maintenance {
    return Maintenance(
        maintenanceDTO.oid,
        convertToModel(maintenanceDTO.facility.ref ?: throw IllegalStateException()),
        maintenanceDTO.visitDate,
        maintenanceDTO.duration,
        maintenanceDTO.maintenanceType,
        maintenanceDTO.state
    )
}

fun convertToDTO(maintenance: Maintenance): MaintenanceDTO {
    return MaintenanceDTO(
        maintenance.oid,
        EntityLink(maintenance.facility.oid, convertToDTO(maintenance.facility)),
        maintenance.visitDate,
        maintenance.duration,
        maintenance.maintenanceType,
        maintenance.state
    )
}

fun convertToModel(facilityDTO: FacilityDTO): Facility {
    return Facility(
        facilityDTO.oid,
        facilityDTO.name,
        facilityDTO.identifier,
        facilityDTO.address,
        facilityDTO.assingmentDate
    )
}

fun convertToDTO(facility: Facility): FacilityDTO {
    return FacilityDTO(
        facility.oid,
        facility.name,
        facility.identifier,
        facility.address,
        facility.assingmentDate
    )
}
