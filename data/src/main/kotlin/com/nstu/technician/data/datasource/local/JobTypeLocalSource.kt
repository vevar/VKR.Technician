package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ImplementsDataSource
import com.nstu.technician.data.datasource.entity.JobTypeDataSource
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.local.dao.JobTypeDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.job.JobTypeDTO
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.until.toJobTypeDTO
import com.nstu.technician.data.until.toJobTypeEntity
import javax.inject.Inject
import javax.inject.Named

class JobTypeLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    @Named(LOCAL)
    private val implementsLocalSource: ImplementsDataSource,
    private val jobTypeDao: JobTypeDao
) : JobTypeDataSource {

    override suspend fun delete(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): JobTypeDTO? {
        return jobTypeDao.findById(id)?.let { jobTypeEntity ->
            val implements = implementsLocalSource.findByJobTypeId(jobTypeEntity.oid)
            jobTypeEntity.toJobTypeDTO(implements)
        }
    }

    override suspend fun save(obj: JobTypeDTO) {
        utilDao.transaction {
            jobTypeDao.save(obj.toJobTypeEntity())
            obj.impList.map { it.getObject() }.forEach {
                implementsLocalSource.saveForJobTypeId(it, obj.oid)
            }
        }
    }
}