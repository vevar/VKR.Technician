package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.BODY_MUST_BE_SET
import com.nstu.technician.data.datasource.cloud.api.MaintenanceApi
import com.nstu.technician.data.datasource.entity.*
import com.nstu.technician.data.dto.job.MaintenanceDTO
import com.nstu.technician.domain.NONE
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

class MaintenanceCloudSource @Inject constructor(
    private val maintenanceApi: MaintenanceApi,
    @Named(CLOUD)
    private val facilityCloudSource: FacilityDataSource,
    @Named(CLOUD)
    private val maintenanceJobCloudSource: MaintenanceJobDataSource,
    @Named(CLOUD)
    private val artifactCloudSource: ArtifactDataSource
) : MaintenanceDataSource {

    override suspend fun findById(id: Long): MaintenanceDTO {
        return (maintenanceApi.getMaintenance(id).execute().body()
            ?: throw IllegalStateException(BODY_MUST_BE_SET)).apply {
            runBlocking { loadDependencies() }
        }
    }

    override suspend fun saveAllForShift(listMaintenance: List<MaintenanceDTO>, shiftId: Long): List<Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findByShiftId(shiftId: Long): List<MaintenanceDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveForShift(maintenanceDTO: MaintenanceDTO, shiftId: Long): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    suspend fun MaintenanceDTO.loadDependencies() {
        val facilityDTO = facility.ref
        if (facilityDTO == null) {
            try {
                facility.ref = facilityCloudSource.findById(facility.oid).apply {
                    facilityCloudSource.loadDependencies(this)
                }
            } catch (e: NotFoundException) {
                throw IllegalStateException("Facility must be set")
            }
        } else {
            facilityCloudSource.loadDependencies(facilityDTO)
        }

        if (parent.oid != NONE && parent.ref == null) {
            parent.ref = maintenanceApi.getMaintenance(parent.oid).execute().body()
                ?: throw IllegalStateException(BODY_MUST_BE_SET)
        }

        jobList.forEach {
            if (it.ref == null) {
                try {
                    it.ref = maintenanceJobCloudSource.findById(it.oid)
                } catch (e: NotFoundException) {
                    throw IllegalStateException("maintenanceJob(oid:$oid) must be set")
                }
            }
        }

        if (voiceMessage.oid != NONE && voiceMessage.ref == null) {
            try {
                voiceMessage.ref = artifactCloudSource.findById(voiceMessage.oid)
            } catch (e: NotFoundException) {
                throw IllegalStateException("voiceMessage must be set")
            }
        }
    }

}