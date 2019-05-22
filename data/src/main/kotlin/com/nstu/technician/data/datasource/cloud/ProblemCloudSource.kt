package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.entity.ProblemDataSource
import com.nstu.technician.data.datasource.local.dao.ProblemDao
import com.nstu.technician.data.dto.ProblemDTO
import com.nstu.technician.data.until.toProblemDTO
import com.nstu.technician.data.until.toProblemEntity
import com.nstu.technician.domain.exceptions.NotFoundException
import javax.inject.Inject

class ProblemCloudSource @Inject constructor(
    private val problemDao: ProblemDao
) : ProblemDataSource {

    companion object {
        const val TAG = "ProblemCloudSource"
    }

    override suspend fun findById(id: Long): ProblemDTO {
        return problemDao.findById(id)?.toProblemDTO() ?: throw NotFoundException(TAG, "problem by id=$id")
    }

    override suspend fun save(obj: ProblemDTO): Long {
        return problemDao.save(obj.toProblemEntity())
    }

    override suspend fun delete(obj: ProblemDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
