package com.nstu.technician.data.until

import com.nstu.technician.data.database.embedded.AddressEmb
import com.nstu.technician.data.database.embedded.GpsObjectEmb
import com.nstu.technician.data.database.entity.ProblemEntity
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
import com.nstu.technician.data.dto.common.GpsObjectDTO
import com.nstu.technician.data.dto.document.ContractDTO
import com.nstu.technician.data.dto.document.ContractorDTO
import com.nstu.technician.data.dto.document.DocumentDTO
import com.nstu.technician.data.dto.job.*
import com.nstu.technician.data.dto.tool.*
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.TechnicianDTO
import com.nstu.technician.data.dto.user.UserDTO
import com.nstu.technician.domain.NONE
import com.nstu.technician.domain.model.MiniShift
import com.nstu.technician.domain.model.Problem
import com.nstu.technician.domain.model.Shift
import com.nstu.technician.domain.model.common.*
import com.nstu.technician.domain.model.document.Contract
import com.nstu.technician.domain.model.document.Contractor
import com.nstu.technician.domain.model.document.Document
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.model.facility.JobType
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.domain.model.tool.*
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User

inline fun <reified F : EntityDTO> EntityLink<F>.getObject(): F {
    return ref ?: throw IllegalStateException("ref(${F::class.java} must be set")
}

fun AccountEntity.toAccountDTO(): AccountDTO {
    return AccountDTO(oid, login, password)
}

fun AccountDTO.toAccountEntity(): AccountEntity {
    return AccountEntity(oid, login, password)
}

fun AccountDTO.toAccount(): Account {
    return Account(
        oid = oid,
        password = password,
        login = login
    )
}

fun Account.toAccountDTO(): AccountDTO {
    return AccountDTO(
        oid = oid,
        login = login,
        password = password
    )
}

fun TechnicianEntity.toTechnicianDTO(userDTO: UserDTO): TechnicianDTO {
    return TechnicianDTO(oid, EntityLink(userDTO), tState = tState)
}

fun TechnicianDTO.toTechnicianEntity(): TechnicianEntity {
    return TechnicianEntity(oid, userId = user.oid, tState = tState)
}

fun TechnicianDTO.toTechnician(): Technician {
    return Technician(
        oid = oid,
        user = user.getObject().toUser(),
        state = tState
    )
}

fun Technician.toTechnicianDTO(): TechnicianDTO {
    return TechnicianDTO(
        oid = oid,
        tState = state,
        user = EntityLink(user.toUserDTO())
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

fun UserDTO.toUserEntity(): UserEntity {
    return UserEntity(
        oid = oid,
        firstName = firstName,
        lastName = lastName,
        middleName = middleName,
        sessionToken = sessionToken,
        accountId = account?.oid ?: throw IllegalStateException("Account must be set")
    )
}

fun UserDTO.toUser(): User {
    return User(
        oid,
        lastName,
        firstName,
        middleName,
        sessionToken
    )
}

fun User.toUserDTO(): UserDTO {
    return UserDTO(
        oid = oid,
        account = account?.let { EntityLink(it.toAccountDTO()) },
        firstName = firstName,
        lastName = lastName,
        middleName = middleName,
        sessionToken = sessionToken
    )
}

fun ArtifactEntity.toArtifactDTO(): ArtifactDTO {
    return ArtifactDTO(
        oid = oid,
        type = type,
        original = original,
        name = name,
        fileSize = fileSize,
        date = OwnDateTime(date)
    )
}

fun ArtifactDTO.toArtifactEntity(): ArtifactEntity {
    return ArtifactEntity(
        oid = oid,
        date = date.timeInMS,
        fileSize = fileSize,
        name = name,
        original = original,
        type = type
    )
}

fun ArtifactDTO.toArtifact(): Artifact {
    return Artifact(
        oid = oid,
        date = date,
        name = name,
        fileSize = fileSize,
        original = original,
        type = type
    )
}

fun Artifact.toArtifactDTO(): ArtifactDTO {
    return ArtifactDTO(
        oid = oid,
        date = date,
        name = name,
        type = type,
        original = original,
        fileSize = fileSize
    )
}

private fun AddressDTO.toAddressEmb(): AddressEmb {
    return AddressEmb(
        type = type,
        city = city,
        home = home,
        location = location.toGpsObjectEmb(),
        office = office,
        street = street
    )
}


fun AddressEntity.toAddressDTO(): AddressDTO {
    return AddressDTO(
        street = street,
        home = home,
        office = office,
        location = gpsObjectEmb.toGpsObjectDTO(),
        type = type,
        city = city
    )
}

private fun GpsObjectEmb.toGpsObjectDTO(): GpsObjectDTO {
    return GpsObjectDTO(
        geoy = latitude,
        geox = longitude
    )
}

fun AddressDTO.toAddress(): Address {
    return Address(
        street = street,
        home = home,
        location = location.toGpsObject(),
        office = office,
        type = type,
        city = city
    )
}

fun Address.toAddressDTO(): AddressDTO {
    return AddressDTO(
        street = street,
        home = home,
        location = location.toGpsObjectDTO(),
        office = office,
        type = type,
        city = city
    )
}

fun FacilityEntity.toFacilityDTO(
    contractDTO: ContractDTO?,
    contractorDTO: ContractorDTO
): FacilityDTO {
    return FacilityDTO(
        oid = oid,
        name = name,
        address = address.toAddressDTO(),
        assingmentDate = assingmentDate,
        contract = contractDTO?.let { EntityLink(it) } ?: EntityLink(0),
        identifier = identifier,
        contractor = EntityLink(contractorDTO)
    )
}

private fun AddressEmb.toAddressDTO(): AddressDTO {
    return AddressDTO(
        type = type,
        street = street,
        office = office,
        location = location.toGpsObjectDTO(),
        home = home,
        city = city
    )
}

fun FacilityDTO.toFacilityEntity(): FacilityEntity {
    return FacilityEntity(
        oid = oid,
        name = name,
        address = address.toAddressEmb(),
        contractId = contract.oid,
        assingmentDate = assingmentDate,
        identifier = identifier,
        contractorId = contractor.oid
    )
}

fun FacilityDTO.toFacility(): Facility {
    return Facility(
        oid = oid,
        name = name,
        address = address.toAddress(),
        assingmentDate = assingmentDate,
        identifier = identifier,
        contract = if (contract.oid == NONE) null else contract.getObject().toContract(),
        contractor = contractor.getObject().toContractor()
    )
}

fun Facility.toFacilityDTO(): FacilityDTO {
    return FacilityDTO(
        oid = oid,
        name = name,
        identifier = identifier,
        contract = contract?.let { EntityLink(it.toContractDTO()) } ?: EntityLink(NONE),
        contractor = EntityLink(contractor.toContractorDTO()),
        address = address.toAddressDTO(),
        assingmentDate = assingmentDate
    )
}

fun ImplementsEntity.toImplementsDTO(): ImplementsDTO {
    return ImplementsDTO(
        oid = oid,
        name = name,
        currentNubmer = currentNumber
    )
}

fun ImplementsDTO.toJobTypeImplementsJoin(jobTypeId: Long): JobTypeImplementsJoin {
    return JobTypeImplementsJoin(
        oid = 0, jobTypeId = jobTypeId, implementsId = oid
    )
}

fun ImplementsDTO.toImplementsEntity(): ImplementsEntity {
    return ImplementsEntity(
        oid = oid,
        name = name,
        currentNumber = currentNubmer
    )
}

fun ImplementsDTO.toImplements(): Implements {
    return Implements(
        oid,
        name,
        currentNubmer
    )
}

fun Implements.toImplementsDTO(): ImplementsDTO {
    return ImplementsDTO(
        oid = oid,
        name = name,
        currentNubmer = currentNumber
    )
}

fun ImplementUnitEntity.toImplementUnitDTO(implementsDTO: ImplementsDTO): ImplementUnitDTO {
    return ImplementUnitDTO(
        oid = oid,
        code = code,
        impl = EntityLink(implementsDTO)
    )
}

fun ImplementUnitDTO.toImplementUnitEntity(): ImplementUnitEntity {
    return ImplementUnitEntity(
        oid = oid,
        code = code,
        implementsId = impl.oid
    )
}

fun ImplementUnitDTO.toImplementUnit(): ImplementUnit {
    return ImplementUnit(
        oid = oid,
        code = code,
        impl = impl.getObject().toImplements()
    )
}

fun ImplementUnit.toImplementUnitDTO(): ImplementUnitDTO {
    return ImplementUnitDTO(
        oid = oid,
        impl = EntityLink(impl.toImplementsDTO()),
        code = code
    )
}


private fun GpsObject.toGpsObjectDTO(): GpsObjectDTO {
    return GpsObjectDTO(
        geoy = latitude,
        geox = longitude
    )
}

private fun GpsObjectDTO.toGpsObject(): GpsObject {
    return GpsObject(
        latitude = geoy,
        longitude = geox
    )
}

private fun GpsObjectDTO.toGpsObjectEmb(): GpsObjectEmb {
    return GpsObjectEmb(
        latitude = geoy,
        longitude = geox
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

fun GPSPointFromShiftEntity.toGpsPointDTO(): GPSPointDTO {
    return GPSPointDTO(
        oid = oid,
        geox = longitude,
        geoy = latitude
    )
}

fun GPSEntity.toGpsPointDTO(): GPSPointDTO {
    return GPSPointDTO(
        oid = oid,
        geox = longitude,
        geoy = latitude
    )
}

fun GPSPointDTO.toGPSPoint(): GPSPoint {
    return GPSPoint(oid, geoy, geox)
}

fun GPSPointDTO.convertToGPSEntity(): GPSEntity {
    return GPSEntity(
        oid = oid,
        latitude = geoy,
        longitude = geox
    )
}

fun GPSPoint.toGpsPointDTO(): GPSPointDTO {
    return GPSPointDTO(
        oid, latitude, longitude
    )
}

fun MaintenanceEntity.toMaintenanceDTO(
    facilityDTO: FacilityDTO,
    jobList: List<MaintenanceJobDTO>,
    parent: MaintenanceDTO?,
    voiceMessage: ArtifactDTO?,
    workCompletionReport: DocumentDTO?
): MaintenanceDTO {
    return MaintenanceDTO(
        oid = oid,
        facility = EntityLink(facilityDTO.oid, facilityDTO),
        visitDate = OwnDateTime(visitDate),
        state = state,
        maintenanceType = maintenanceType,
        duration = duration,
        endTime = OwnDateTime(endTime ?: NONE),
        beginTime = OwnDateTime(beginTime ?: NONE),
        jobList = jobList.map { EntityLink(it) },
        parent = parent?.let { EntityLink(it) } ?: EntityLink(NONE),
        voiceMessage = voiceMessage?.let { EntityLink(it) } ?: EntityLink(NONE),
        workCompletionReport = workCompletionReport?.let { EntityLink(it) } ?: EntityLink(NONE)
    )
}

fun MaintenanceDTO.toMaintenanceEntity(shiftId: Long): MaintenanceEntity {
    return MaintenanceEntity(
        oid = oid,
        beginTime = beginTime.timeInMS,
        endTime = endTime.timeInMS,
        facilityId = facility.oid,
        duration = duration,
        maintenanceType = maintenanceType,
        state = state,
        visitDate = visitDate.timeInMS,
        maintenanceParentId = if (parent.oid != NONE) parent.oid else null,
        voiceMessageId = if (voiceMessage.oid != NONE) voiceMessage.oid else null,
        workCompletionReportId = workCompletionReport.oid,
        shiftId = shiftId
    )
}

fun MaintenanceDTO.toMaintenance(): Maintenance {
    return Maintenance(
        oid = oid,
        facility = facility.getObject().toFacility(),
        visitDate = visitDate,
        state = state,
        maintenanceType = maintenanceType,
        duration = duration,
        endTime = endTime,
        beginTime = beginTime,
        jobList = jobList.map { it.getObject().toMaintenanceJob() },
        parent = parent.ref?.toMaintenance(),
        voiceMassage = voiceMessage.ref?.toArtifact(),
        workCompletionReport = workCompletionReport.ref?.toDocument()
    )
}

fun Maintenance.toMaintenanceDTO(): MaintenanceDTO {
    return MaintenanceDTO(
        oid = oid,
        facility = EntityLink(facility.toFacilityDTO()),
        state = state,
        duration = duration,
        endTime = endTime ?: OwnDateTime(NONE),
        beginTime = beginTime ?: OwnDateTime(NONE),
        jobList = jobList.map { EntityLink(it.toMaintenanceJobDTO()) },
        voiceMessage = voiceMassage?.let { (EntityLink(it.toArtifactDTO())) } ?: EntityLink(NONE),
        parent = parent?.let { EntityLink(it.toMaintenanceDTO()) } ?: EntityLink(NONE),
        maintenanceType = maintenanceType,
        visitDate = visitDate,
        workCompletionReport = workCompletionReport?.let { EntityLink(it.toDocumentDTO()) } ?: EntityLink(NONE)
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

fun ShiftDTO.toShiftEntity(): ShiftEntity {
    return ShiftEntity(
        oid, date
    )
}

fun ShiftDTO.toShiftModel(): Shift {
    return Shift(
        oid = oid,
        date = date,
        points = points.map { it.getObject().toGPSPoint() },
        visits = visits.map { it.getObject().toMaintenance() }
    )
}

fun ShiftDTO.toMiniShift(): MiniShift {
    return MiniShift(
        oid = oid,
        date = date
    )
}

fun Shift.toShiftDTO(): ShiftDTO {
    return ShiftDTO(
        oid = oid,
        date = date,
        visits = visits.map { EntityLink(it.toMaintenanceDTO()) },
        points = points.map { EntityLink(it.toGpsPointDTO()) }
    )
}

fun ShiftDTO.ToMiniShift(): MiniShift {
    return MiniShift(
        oid = oid,
        date = date
    )
}

fun ContractEntity.toContractDTO(
    artifactDTO: ArtifactDTO
): ContractDTO {
    return ContractDTO(
        oid = oid,
        state = state,
        docscan = EntityLink(artifactDTO),
        date = OwnDateTime(date),
        number = number,
        docType = docType
    )
}

fun ContractDTO.toContractEntity(): ContractEntity {
    return ContractEntity(
        oid = oid,
        state = state,
        number = number,
        docType = docType,
        date = date.timeInMS,
        artifactId = docscan.oid
    )
}

fun ContractDTO.toContract(): Contract {
    return Contract(
        oid = oid,
        state = state,
        number = number,
        docType = docType,
        date = date,
        artifact = docscan.getObject().toArtifact()
    )
}

private fun Contract.toContractDTO(): ContractDTO {
    return ContractDTO(
        oid = oid,
        state = state,
        number = number,
        docType = docType,
        docscan = EntityLink(artifact.toArtifactDTO()),
        date = date
    )
}

fun ContractorEntity.toContractorDTO(): ContractorDTO {
    return ContractorDTO(
        oid = oid,
        name = name,
        INN = INN,
        address = addressEmb.toAddressDTO()
    )
}

fun ContractorDTO.toContractorEntity(): ContractorEntity {
    return ContractorEntity(
        oid = oid,
        INN = INN,
        name = name,
        addressEmb = address.toAddressEmb()
    )
}

fun ContractorDTO.toContractor(): Contractor {
    return Contractor(
        oid = oid,
        address = address.toAddress(),
        name = name,
        INN = INN
    )
}

fun Contractor.toContractorDTO(): ContractorDTO {
    return ContractorDTO(
        oid = oid,
        address = address.toAddressDTO(),
        name = name,
        INN = INN
    )
}

fun MaintenanceJobEntity.toMaintenanceJobDTO(
    jobTypeDTO: JobTypeDTO?,
    components: List<ComponentUnitDTO>?,
    implList: List<ImplementsDTO>?,
    beginPhoto: ArtifactDTO?,
    endPhoto: ArtifactDTO?,
    problemDTO: ProblemDTO?
): MaintenanceJobDTO {
    return MaintenanceJobDTO(
        oid = oid,
        endTime = endTime?.let { OwnDateTime(it) } ?: OwnDateTime(NONE),
        duration = duration,
        beginTime = beginTime?.let { OwnDateTime(it) } ?: OwnDateTime(NONE),
        jobState = jobState,
        jobType = EntityLink(jobTypeDTO ?: throw IllegalStateException("jobTypeDTO must be set")),
        beginPhoto = beginPhoto?.let { EntityLink(it) } ?: EntityLink(NONE),
        endPhoto = endPhoto?.let { EntityLink(it) } ?: EntityLink(NONE),
        problem = problemDTO?.let { EntityLink(it) } ?: EntityLink(NONE),
        components = components?.map { EntityLink(it) } ?: throw IllegalStateException("components must be set"),
        implList = implList?.map { EntityLink(it) } ?: throw IllegalStateException("implList must be set")
    )
}

fun MaintenanceJobDTO.toMaintenanceJobEntity(maintenanceJobId: Long): MaintenanceJobEntity {
    return MaintenanceJobEntity(
        oid = oid,
        jobState = jobState,
        jobTypeId = jobType.oid,
        beginPhotoId = if (beginPhoto.oid != NONE) beginPhoto.oid else null,
        endPhotoId = if (endPhoto.oid != NONE) endPhoto.oid else null,
        beginTime = beginTime.timeInMS,
        duration = duration,
        endTime = endTime.timeInMS,
        problemId = if (problem.oid != NONE) problem.oid else null,
        maintenanceId = maintenanceJobId
    )
}

fun MaintenanceJobDTO.toMaintenanceJob(): MaintenanceJob {
    return MaintenanceJob(
        oid = oid,
        duration = duration,
        beginPhoto = beginPhoto.ref?.toArtifact(),
        endPhoto = endPhoto.ref?.toArtifact(),
        jobState = jobState,
        components = components.map { it.getObject().toComponentUnit() },
        implList = implList.map { it.getObject().toImplements() },
        problem = problem.ref?.toProblem(),
        jobType = jobType.getObject().toJobType(),
        beginTime = beginTime,
        endTime = endTime
    )
}

fun MaintenanceJob.toMaintenanceJobDTO(): MaintenanceJobDTO {
    return MaintenanceJobDTO(
        oid = oid,
        beginTime = beginTime ?: OwnDateTime(NONE),
        endTime = endTime ?: OwnDateTime(NONE),
        duration = duration,
        jobType = EntityLink(jobType.toJobTypeDTO()),
        problem = problem?.let { EntityLink(it.toProblemDTO()) } ?: EntityLink(NONE),
        implList = implList.map { EntityLink(it.toImplementsDTO()) },
        jobState = jobState,
        components = components.map { EntityLink(it.toComponentUnitDTO()) },
        endPhoto = endPhoto?.let { EntityLink(it.toArtifactDTO()) } ?: EntityLink(NONE),
        beginPhoto = beginPhoto?.let { EntityLink(it.toArtifactDTO()) } ?: EntityLink(NONE)
    )
}

fun ComponentUnitEntity.toComponentUnitDTO(componentDTO: ComponentDTO): ComponentUnitDTO {
    return ComponentUnitDTO(
        oid = oid,
        number = number,
        component = EntityLink(componentDTO)
    )
}

fun ComponentUnitDTO.toComponentUnitEntity(maintenanceJobId: Long): ComponentUnitEntity {
    return ComponentUnitEntity(
        oid = oid,
        number = number,
        maintenanceJobId = maintenanceJobId,
        componentId = component.oid
    )
}

fun ComponentUnitDTO.toComponentUnit(): ComponentUnit {
    return ComponentUnit(
        oid = oid,
        component = component.getObject().toComponent(),
        number = number
    )
}

private fun ComponentUnit.toComponentUnitDTO(): ComponentUnitDTO {
    return ComponentUnitDTO(
        oid = oid,
        number = number,
        component = EntityLink(component.toComponentDTO())
    )
}

fun ComponentEntity.toComponentDTO(componentTypeDTO: ComponentTypeDTO): ComponentDTO {
    return ComponentDTO(
        oid = oid,
        name = name,
        type = EntityLink(componentTypeDTO)
    )
}

fun ComponentDTO.toComponentEntity(): ComponentEntity {
    return ComponentEntity(
        oid = oid,
        name = name,
        componentTypeId = type.oid
    )
}

fun ComponentDTO.toComponent(): Component {
    return Component(
        oid = oid,
        name = name,
        type = type.getObject().toComponentType()
    )
}

fun Component.toComponentDTO(): ComponentDTO {
    return ComponentDTO(
        oid = oid,
        name = name,
        type = EntityLink(type.toComponentTypeDTO())
    )
}

fun ComponentTypeEntity.toComponentTypeDTO(): ComponentTypeDTO {
    return ComponentTypeDTO(
        oid = oid,
        name = name
    )
}

fun ComponentTypeDTO.toComponentTypeEntity(): ComponentTypeEntity {
    return ComponentTypeEntity(
        oid = oid,
        name = name
    )
}

fun ComponentTypeDTO.toComponentType(): ComponentType {
    return ComponentType(
        oid = oid,
        name = name
    )
}

fun ComponentType.toComponentTypeDTO(): ComponentTypeDTO {
    return ComponentTypeDTO(
        oid = oid,
        name = name
    )
}

fun JobTypeEntity.toJobTypeDTO(implements: List<ImplementsDTO>): JobTypeDTO {
    return JobTypeDTO(
        oid = oid,
        name = name,
        description = description,
        duration = duration,
        impList = implements.map { implementsDTO -> EntityLink(implementsDTO) }
    )
}

fun JobTypeDTO.toJobTypeEntity(): JobTypeEntity {
    return JobTypeEntity(
        oid, name, description, duration
    )
}

fun JobTypeDTO.toJobType(): JobType {
    return JobType(
        oid = oid,
        name = name,
        duration = duration,
        description = description,
        impList = impList.map { it.getObject().toImplements() }
    )
}

fun JobType.toJobTypeDTO(): JobTypeDTO {
    return JobTypeDTO(
        oid = oid,
        name = name,
        duration = duration,
        description = description,
        impList = impList?.map { EntityLink(it.toImplementsDTO()) } ?: mutableListOf()
    )
}

fun ProblemDTO.toProblemEntity(): ProblemEntity{
    return ProblemEntity(
        oid = oid,
        problemType = problemType,
        comment = comment
    )
}

fun ProblemEntity.toProblemDTO(): ProblemDTO {
    return ProblemDTO(
        oid = oid,
        problemType = problemType,
        comment = comment
    )
}

fun ProblemDTO.toProblem(): Problem {
    return Problem(
        oid = oid,
        comment = comment,
        type = problemType
    )
}

fun Problem.toProblemDTO(): ProblemDTO {
    return ProblemDTO(
        oid = oid,
        problemType = type,
        comment = comment
    )
}

fun DocumentDTO.toDocument(): Document {
    return Document(
        oid = oid,
        date = date,
        artifact = docscan?.getObject()?.toArtifact(),
        docType = docType,
        number = number,
        state = state
    )
}

fun Document.toDocumentDTO(): DocumentDTO {
    return DocumentDTO(
        oid = oid,
        date = date,
        state = state,
        docscan = artifact?.let { EntityLink(it.toArtifactDTO()) },
        docType = docType,
        number = number
    )
}