package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.JobTypeDataSource
import com.nstu.technician.data.datasource.local.dao.JobTypeDao
import com.nstu.technician.data.dto.job.JobTypeDTO
import com.nstu.technician.data.until.convertToJobTypeDTO
import com.nstu.technician.data.until.convertToJobTypeEntity
import javax.inject.Inject

class JobTypeLocalSource @Inject constructor(
    private val jobTypeDao: JobTypeDao
) : JobTypeDataSource {

    override suspend fun findById(id: Long): JobTypeDTO? {
        return jobTypeDao.findById(id).convertToJobTypeDTO()
    }

    override suspend fun save(obj: JobTypeDTO) {
        jobTypeDao.save(obj.convertToJobTypeEntity())
    }
}