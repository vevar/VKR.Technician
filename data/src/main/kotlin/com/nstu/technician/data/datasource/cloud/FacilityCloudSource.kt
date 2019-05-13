package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.BODY_MUST_BE_SET
import com.nstu.technician.data.datasource.cloud.api.FacilityApi
import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.ContractDataSource
import com.nstu.technician.data.datasource.entity.ContractorDataSource
import com.nstu.technician.data.datasource.entity.FacilityDataSource
import com.nstu.technician.data.dto.job.FacilityDTO
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

class FacilityCloudSource @Inject constructor(
    private val facilityApi: FacilityApi,
    @Named(CLOUD)
    private val contractCloudSource: ContractDataSource,
    @Named(CLOUD)
    private val contractorCloudSource: ContractorDataSource
) : FacilityDataSource {

    override suspend fun delete(obj: FacilityDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(obj: FacilityDTO): Long {
        return facilityApi.addFacility(obj).execute().body() ?: throw IllegalStateException(BODY_MUST_BE_SET)
    }

    override suspend fun findById(id: Long): FacilityDTO {
        return (facilityApi.getFacilityById(id).execute().body()
            ?: throw IllegalStateException(BODY_MUST_BE_SET)).apply {
            runBlocking { loadDependencies(this@apply) }
        }
    }

    override suspend fun loadDependencies(facilityDTO: FacilityDTO) {
        facilityDTO.apply {
            if (contract.ref == null) {
                try {
                    contract.ref = contractCloudSource.findById(contract.oid)
                } catch (e: NotFoundException) {
                    throw IllegalStateException("Contract must be set")
                }
            }
            if (contractor.ref == null) {
                try {
                    contractor.ref = contractorCloudSource.findById(contractor.oid)
                } catch (e: NotFoundException) {
                    throw IllegalStateException("Contractor must be set")
                }
            }
        }
    }
}