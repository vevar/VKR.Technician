package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ImplementsDataSource
import com.nstu.technician.data.datasource.local.dao.ImplementDao
import com.nstu.technician.data.dto.tool.ImplementsDTO
import com.nstu.technician.data.until.toImplementsDTO
import com.nstu.technician.data.until.toImplementsEntity
import com.nstu.technician.domain.exceptions.NotFoundException
import javax.inject.Inject

class ImplementsLocalSource @Inject constructor(
    private val implementDao: ImplementDao
) : ImplementsDataSource {

    companion object{
        private const val TAG = "ImplementsLocalSource"
    }

    override suspend fun saveAll(list: List<ImplementsDTO>): List<Long> {
        return implementDao.saveAll(list.map { it.toImplementsEntity() })
    }

    override suspend fun deleteAll() {
        return implementDao.nukeTable()
    }

    override suspend fun findAll(): List<ImplementsDTO> {
        return implementDao.findAll().map { it.toImplementsDTO() }
    }

    override suspend fun findById(id: Long): ImplementsDTO {
        return implementDao.findById(id)?.toImplementsDTO()?: throw NotFoundException(TAG, "ContractDTO by id($id)")
    }

    override suspend fun save(obj: ImplementsDTO): Long {
        return implementDao.save(obj.toImplementsEntity())
    }

    override suspend fun delete(obj: ImplementsDTO) {
        return implementDao.delete(obj.toImplementsEntity())
    }

    override suspend fun saveForJobTypeId(implementsDTO: ImplementsDTO, jobTypeId: Long): Long {
        val implementsId = implementDao.save(implementsDTO.toImplementsEntity())
        implementDao.saveForJobTypeId(jobTypeId, implementsDTO.oid)
        return implementsId
    }

    override suspend fun findByJobTypeId(jobTypeId: Long): List<ImplementsDTO> {
        return implementDao.findByJobTypeId(jobTypeId)
            .map { implementsEntity -> implementsEntity.toImplementsDTO() }
    }

    override suspend fun saveForMaintenanceJob(implements: ImplementsDTO, maintenanceJobId: Long): Long {
        val implementsId = implementDao.save(implements.toImplementsEntity())
        implementDao.saveForMaintenanceJob(maintenanceJobId, implements.oid)
        return implementsId
    }

    override suspend fun findByMaintenanceJobId(maintenanceJobId: Long): List<ImplementsDTO> {
        return implementDao.findByMaintenanceJobId(maintenanceJobId).map { implementsEntity ->
            implementsEntity.toImplementsDTO()
        }
    }
}