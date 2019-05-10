package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.*
import com.nstu.technician.data.datasource.local.dao.FacilityDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.common.AddressDTO
import com.nstu.technician.data.dto.document.ContractDTO
import com.nstu.technician.data.dto.document.ContractorDTO
import com.nstu.technician.data.dto.job.FacilityDTO
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.until.toFacilityDTO
import com.nstu.technician.data.until.toFacilityEntity
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

class FacilityLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val facilityDao: FacilityDao,
    @Named(LOCAL)
    private val addressLocalSource: AddressDataSource,
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
            var addressDTO: AddressDTO? = null
            var contractorDTO: ContractorDTO? = null
            var contractDTO: ContractDTO? = null
            runBlocking {
                addressDTO = addressLocalSource.findById(facilityEntity.addressId)
                contractorDTO = contractorLocalSource.findById(facilityEntity.contractorId)
                contractDTO = facilityEntity.contractId?.let { contractLocalSource.findById(it) }
            }
            facilityEntity.toFacilityDTO(addressDTO!!, contractDTO!!, contractorDTO!!)
        } ?: throw NotFoundException(TAG, "FacilityDTO by id($id)")
    }


    override suspend fun save(obj: FacilityDTO): Long {
        return utilDao.transactionSave {
            runBlocking {
                addressLocalSource.save(obj.address)
                contractorLocalSource.save(obj.contractor.getObject())
                contractLocalSource.save(obj.contract.getObject())
            }
            facilityDao.save(obj.toFacilityEntity())
        }
    }

}