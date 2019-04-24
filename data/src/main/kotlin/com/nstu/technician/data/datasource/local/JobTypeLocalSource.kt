package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.ImplementsDataSource
import com.nstu.technician.data.datasource.JobTypeDataSource
import com.nstu.technician.data.datasource.LOCAL
import com.nstu.technician.data.datasource.local.dao.JobTypeDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.job.JobTypeDTO
import com.nstu.technician.data.until.convertToJobTypeDTO
import com.nstu.technician.data.until.convertToJobTypeEntity
import com.nstu.technician.data.until.getObject
import javax.inject.Inject
import javax.inject.Named

class JobTypeLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    @Named(LOCAL)
    private val implementsLocalSource: ImplementsDataSource,
    private val jobTypeDao: JobTypeDao
) : JobTypeDataSource {

    override suspend fun findById(id: Long): JobTypeDTO? {
        return jobTypeDao.findById(id)?.let { jobTypeEntity ->
            val implements = implementsLocalSource.findByJobTypeId(jobTypeEntity.oid)
            jobTypeEntity.convertToJobTypeDTO(implements)
        }
    }

    override suspend fun save(obj: JobTypeDTO) {
        utilDao.transaction {
            jobTypeDao.save(obj.convertToJobTypeEntity())
            obj.impList.map { it.getObject() }.forEach {
                implementsLocalSource.saveForJobTypeId(it, obj.oid)
            }
        }
    }
}