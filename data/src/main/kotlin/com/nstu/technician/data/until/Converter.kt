package com.nstu.technician.data.until

import com.nstu.technician.data.database.entity.ShiftEntity
import com.nstu.technician.data.database.entity.common.AddressEntity
import com.nstu.technician.data.database.entity.common.ArtifactEntity
import com.nstu.technician.data.database.entity.common.GPSEntity
import com.nstu.technician.data.database.entity.document.ContractEntity
import com.nstu.technician.data.database.entity.document.ContractorEntity
import com.nstu.technician.data.database.entity.job.*
import com.nstu.technician.data.database.entity.tool.*
import com.nstu.technician.data.database.entity.user.AccountEntity
import com.nstu.technician.data.database.entity.user.TechnicianEntity
import com.nstu.technician.data.database.entity.user.UserEntity
import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.ProblemDTO
import com.nstu.technician.data.dto.common.AddressDTO
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.data.dto.common.GPSPointDTO
import com.nstu.technician.data.dto.document.ContractDTO
import com.nstu.technician.data.dto.document.ContractorDTO
import com.nstu.technician.data.dto.job.*
import com.nstu.technician.data.dto.tool.*
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.TechnicianDTO
import com.nstu.technician.data.dto.user.UserDTO
import com.nstu.technician.domain.model.MiniShift
import com.nstu.technician.domain.model.Problem
import com.nstu.technician.domain.model.Shift
import com.nstu.technician.domain.model.common.Address
import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.document.Contract
import com.nstu.technician.domain.model.document.Contractor
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.model.facility.JobType
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.domain.model.tool.*
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User

fun convertToDTO(account: Account): AccountDTO {
    return AccountDTO(account.oid, account.login, account.password)
}

fun convertToModel(account: AccountDTO): Account {
    return Account(account.oid, account.login, account.password)
}

fun AccountEntity.convertToAccountDTO(): AccountDTO {
    return AccountDTO(oid, login, password)
}

fun AccountDTO.convertToAccountEntity(): AccountEntity {
    return AccountEntity(oid, login, password)
}

fun TechnicianDTO.convertToTechnicianEntity(): TechnicianEntity {
    return TechnicianEntity(oid, userId = user.oid, tState = tState)
}

fun TechnicianEntity.convertToTechnicianDTO(userDTO: UserDTO): TechnicianDTO {
    return TechnicianDTO(oid, EntityLink(userDTO), tState = tState)
}

fun UserDTO.convertToUserEntity(): UserEntity {
    return UserEntity(
        oid = oid,
        firstName = firstName,
        lastName = lastName,
        middleName = middleName,
        sessionToken = sessionToken,
        accountId = account.oid
    )
}

fun UserEntity.convertToUserDTO(account: AccountDTO): UserDTO {
    return UserDTO(
        oid = oid,
        sessionToken = sessionToken,
        middleName = middleName,
        lastName = lastName,
        firstName = firstName,
        account = EntityLink(account)
    )
}

fun ArtifactEntity.convertToArtifactDTO(): ArtifactDTO {
    return ArtifactDTO(
        oid = oid,
        type = type,
        original = original,
        name = name,
        fileSize = fileSize,
        date = OwnDateTime(date)
    )
}

fun ArtifactDTO.convertToArtifactEntity(): ArtifactEntity {
    return ArtifactEntity(
        oid = oid,
        date = date.timeInMS,
        fileSize = fileSize,
        name = name,
        original = original,
        type = type
    )
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
        convertToModel(technicianDTO.user.ref ?: throw IllegalStateException("User must be set")),
        technicianDTO.tState
    )
}

fun convertToModel(facilityDTO: FacilityDTO): Facility {
    val facility = Facility(
        facilityDTO.oid,
        facilityDTO.identifier,
        facilityDTO.name,
        facilityDTO.address.convertToAddress(),
        facilityDTO.assingmentDate
    )

    return facility
}

inline fun <reified F : EntityDTO> EntityLink<F>.getObject(): F {
    return ref ?: throw IllegalStateException("ref(${F::class.java} must be set")
}

fun ShiftDTO.convertToShiftEntity(): ShiftEntity {
    return ShiftEntity(
        oid, date
    )
}

fun ShiftDTO.convertToShiftModel(): Shift {
    return Shift(
        oid = oid,
        date = date,
        points = points.map { it.getObject().convertToGPSPoint() },
        visits = visits.map { it.getObject().convertToMaintenance() }
    )
}

fun ShiftDTO.convertToMiniShift(): MiniShift {
    return MiniShift(
        oid = oid,
        date = date
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
        assingmentDate = assingmentDate,
        identifier = identifier
    )
}

fun ImplementsDTO.convertToJobTypeImplementsJoin(jobTypeId: Long): JobTypeImplementsJoin {
    return JobTypeImplementsJoin(
        oid = 0, jobTypeId = jobTypeId, implementsId = oid
    )
}

fun ImplementsDTO.convertToImplementsEntity(): ImplementsEntity {
    return ImplementsEntity(
        oid = oid,
        name = name,
        currentNumber = currentNubmer
    )
}

fun ImplementsDTO.convertToImplement(): Implements {
    return Implements(
        oid, name, currentNubmer
    )
}

fun ImplementsEntity.convertToImplementsDTO(): ImplementsDTO {
    return ImplementsDTO(
        oid = oid,
        name = name,
        currentNubmer = currentNumber
    )
}

fun ImplementUnitDTO.convertToImplementUnit(): ImplementUnit {
    return ImplementUnit(
        oid, code
    )
}

fun ImplementUnitDTO.convertToImplementUnitEntity(): ImplementUnitEntity {
    return ImplementUnitEntity(
        oid = oid,
        code = code,
        implementsId = impl.oid
    )
}

fun ImplementUnitEntity.convertToImplementUnitDTO(implementsDTO: ImplementsDTO): ImplementUnitDTO {
    return ImplementUnitDTO(
        oid = oid,
        code = code,
        impl = EntityLink(implementsDTO)
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
        facility = facility.getObject().convertToFacility(),
        visitDate = visitDate,
        state = state,
        maintenanceType = maintenanceType,
        duration = duration,
        endTime = endTime,
        beginTime = beginTime,
        jobList = jobList.map { it.getObject().convertToMaintenanceJob() })
}

fun FacilityDTO.convertToFacility(): Facility {
    return Facility(
        oid = oid,
        name = name,
        address = address.convertToAddress(),
        assingmentDate = assingmentDate,
        identifier = identifier,
        contract = contract?.getObject()?.convertToContract()
    )
}

fun ShiftEntity.convertToShiftDTO(points: List<GPSPointDTO>, visits: List<MaintenanceDTO>): ShiftDTO {
    return ShiftDTO(
        oid = oid,
        date = date,
        points = points.map { EntityLink(it) },
        visits = visits.map { EntityLink(it) }
    )
}

fun MaintenanceEntity.convertToMaintenanceDTO(
    facilityDTO: FacilityDTO,
    jobList: List<MaintenanceJobDTO>,
    parent: MaintenanceDTO?,
    voiceMessage: ArtifactDTO?
): MaintenanceDTO {
    return MaintenanceDTO(
        oid = oid,
        facility = EntityLink(facilityDTO.oid, facilityDTO),
        visitDate = OwnDateTime(visitDate),
        state = state,
        maintenanceType = maintenanceType,
        duration = duration,
        endTime = if (endTime != null) OwnDateTime(endTime) else null,
        beginTime = if (beginTime != null) OwnDateTime(beginTime) else null,
        jobList = jobList.map { EntityLink(it) },
        parent = parent?.let { EntityLink(it) },
        voiceMassage = voiceMessage?.let { EntityLink(it) }
    )
}

fun FacilityEntity.convertToFacilityDTO(addressDTO: AddressDTO, contractDTO: ContractDTO): FacilityDTO {
    return FacilityDTO(
        oid = oid,
        name = name,
        address = addressDTO,
        assingmentDate = assingmentDate,
        contract = EntityLink(contractDTO),
        identifier = identifier
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

fun ContractEntity.convertToContractDTO(
    artifactDTO: ArtifactDTO,
    contractorDTO: ContractorDTO,
    facilityDTO: FacilityDTO
): ContractDTO {
    return ContractDTO(
        oid = oid,
        state = state,
        artifact = EntityLink(artifactDTO),
        date = OwnDateTime(date),
        number = number,
        docType = docType,
        contractor = EntityLink(contractorDTO),
        facility = EntityLink(facilityDTO)
    )
}

fun ContractDTO.convertToContractEntity(): ContractEntity {
    return ContractEntity(
        oid = oid,
        state = state,
        number = number,
        docType = docType,
        date = date.timeInMS,
        artifactId = artifact.oid,
        contractorId = contractor.oid,
        facilityId = facility.oid
    )
}

fun ContractDTO.convertToContract(): Contract {
    return Contract(
        oid = oid,
        state = state,
        number = number,
        docType = docType,
        date = date,
        artifact = artifact.getObject().convertToArtifact(),
        contractor = contractor.getObject().convertToContractor(),
        facility = facility.getObject().convertToFacility()
    )
}

fun ContractorEntity.convertToContractorDTO(addressDTO: AddressDTO): ContractorDTO {
    return ContractorDTO(
        oid = oid,
        name = name,
        INN = INN,
        address = addressDTO
    )
}

fun ContractorDTO.convertToContractorEntity(): ContractorEntity {
    return ContractorEntity(
        oid = oid,
        INN = INN,
        name = name,
        addressId = address.location.oid
    )
}

fun ContractorDTO.convertToContractor(): Contractor {
    return Contractor(
        oid = oid,
        address = address.convertToAddress(),
        name = name,
        INN = INN
    )
}

fun GPSPointDTO.convertToGPSPointFromShiftEntity(shiftId: Long): GPSPointFromShiftEntity {
    return GPSPointFromShiftEntity(
        oid = oid,
        latitude = geoy,
        longitude = geox,
        shiftId = shiftId
    )
}

fun GPSPointFromShiftEntity.convertToGpsPointDTO(): GPSPointDTO {
    return GPSPointDTO(
        oid = oid,
        geox = longitude,
        geoy = latitude
    )
}

fun MaintenanceJobDTO.convertToMaintenanceJob(): MaintenanceJob {
    return MaintenanceJob(
        oid = oid,
        duration = duration,
        beginPhoto = beginPhoto?.ref?.convertToArtifact(),
        endPhoto = endPhoto?.ref?.convertToArtifact(),
        jobState = jobState,
        components = components?.map { it.getObject().convertToComponentUnit() },
        implList = implList.map { it.getObject().convertToImplement() },
        problem = problem?.ref?.convertToProblem(),
        jobType = jobType.getObject().convertToJobType(),
        beginTime = beginTime,
        endTime = endTime
    )
}

fun ArtifactDTO.convertToArtifact(): Artifact {
    return Artifact(
        oid = oid,
        date = date,
        name = name,
        fileSize = fileSize,
        original = original,
        type = type
    )
}

fun MaintenanceJobDTO.convertToMaintenanceJobEntity(maintenanceJobId: Long): MaintenanceJobEntity {
    return MaintenanceJobEntity(
        oid = oid,
        jobState = jobState,
        jobTypeId = jobType.oid,
        beginPhotoId = beginPhoto?.oid,
        endPhotoId = endPhoto?.oid,
        beginTime = beginTime?.timeInMS,
        duration = duration,
        endTime = endTime?.timeInMS,
        problemId = problem?.oid,
        maintenanceId = maintenanceJobId
    )
}

fun MaintenanceJobEntity.convertToMaintenanceJobDTO(
    jobTypeDTO: JobTypeDTO,
    components: List<ComponentUnitDTO>,
    implList: List<ImplementsDTO>,
    beginPhoto: ArtifactDTO?,
    endPhoto: ArtifactDTO?,
    problemDTO: ProblemDTO?
): MaintenanceJobDTO {
    return MaintenanceJobDTO(
        oid = oid,
        endTime = endTime?.let { OwnDateTime(it) },
        duration = duration,
        beginTime = beginTime?.let { OwnDateTime(it) },
        jobState = jobState,
        jobType = EntityLink(jobTypeDTO),
        beginPhoto = beginPhoto?.let { EntityLink(it) },
        endPhoto = endPhoto?.let { EntityLink(it) },
        problem = problemDTO?.let { EntityLink(it) },
        components = components.map { EntityLink(it) },
        implList = implList.map { EntityLink(it) }
    )
}

fun ComponentUnitEntity.convertToComponentUnitDTO(componentDTO: ComponentDTO): ComponentUnitDTO {
    return ComponentUnitDTO(
        oid = oid,
        number = number,
        component = EntityLink(componentDTO)
    )
}

fun ComponentUnitDTO.convertToComponentUnitEntity(maintenanceJobId: Long): ComponentUnitEntity {
    return ComponentUnitEntity(
        oid = oid,
        number = number,
        maintenanceJobId = maintenanceJobId,
        componentId = component.oid
    )
}

fun ComponentUnitDTO.convertToComponentUnit(): ComponentUnit {
    return ComponentUnit(
        oid = oid,
        component = component.getObject().convertToComponent(),
        number = number
    )
}

fun ComponentDTO.convertToComponentEntity(): ComponentEntity {
    return ComponentEntity(
        oid = oid,
        name = name,
        componentTypeId = type.oid
    )
}

fun ComponentDTO.convertToComponent(): Component {
    return Component(
        oid = oid,
        name = name,
        type = type.getObject().convertToComponentType()
    )
}

fun ComponentEntity.convertToComponentDTO(componentTypeDTO: ComponentTypeDTO): ComponentDTO {
    return ComponentDTO(
        oid = oid,
        name = name,
        type = EntityLink(componentTypeDTO)
    )
}

fun ComponentTypeEntity.convertToComponentTypeDTO(): ComponentTypeDTO {
    return ComponentTypeDTO(
        oid = oid,
        name = name
    )
}

fun ComponentTypeDTO.convertToComponentTypeEntity(): ComponentTypeEntity {
    return ComponentTypeEntity(
        oid = oid,
        name = name
    )
}

fun ComponentTypeDTO.convertToComponentType(): ComponentType {
    return ComponentType(
        oid = oid,
        name = name
    )
}

fun JobTypeEntity.convertToJobTypeDTO(implements: List<ImplementsDTO>): JobTypeDTO {
    return JobTypeDTO(
        oid = oid,
        name = name,
        description = description,
        duration = duration,
        impList = implements.map { implementsDTO -> EntityLink(implementsDTO) }
    )
}

fun JobTypeDTO.convertToJobTypeEntity(): JobTypeEntity {
    return JobTypeEntity(
        oid, name, description, duration
    )
}

fun JobTypeDTO.convertToJobType(): JobType {
    return JobType(
        oid = oid,
        name = name,
        duration = duration,
        description = description,
        impList = impList.map { it.getObject().convertToImplement() }
    )
}

fun ProblemDTO.convertToProblem(): Problem {
    return Problem(
        oid = oid,
        comment = comment,
        type = type
    )
}