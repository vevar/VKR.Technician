package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ContractDataSource
import com.nstu.technician.data.datasource.entity.ContractorDataSource
import com.nstu.technician.data.datasource.entity.FacilityDataSource
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.local.dao.FacilityDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.document.ContractorDTO
import com.nstu.technician.data.dto.job.FacilityDTO
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.until.toFacilityDTO
import com.nstu.technician.data.until.toFacilityEntity
import com.nstu.technician.domain.NONE
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

class FacilityLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val facilityDao: FacilityDao,
    @Named(LOCAL)
    private val contractLocalSource: ContractDataSource,
    @Named(LOCAL)
    private val contractorLocalSource: ContractorDataSource
) : FacilityDataSource {

    companion object {
        private const val TAG = "FacilityLocalSource"
    }

    override suspend fun delete(obj: FacilityDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): FacilityDTO {
        return facilityDao.findById(id)?.let { facilityEntity ->
            var contractorDTO: ContractorDTO? = null
            runBlocking {
                contractorDTO = contractorLocalSource.findById(facilityEntity.contractorId)
            }
            facilityEntity.toFacilityDTO( contractorDTO!!)
        } ?: throw NotFoundException(TAG, "FacilityDTO by id($id)")
    }

    override suspend fun save(obj: FacilityDTO): Long {
        return utilDao.transactionSave {
            runBlocking {
                if (obj.contractor.oid != NONE) {
                    contractorLocalSource.save(obj.contractor.getObject())
                }
            }
            facilityDao.save(obj.toFacilityEntity())
        }
    }

    override suspend fun loadDependencies(facilityDTO: FacilityDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}