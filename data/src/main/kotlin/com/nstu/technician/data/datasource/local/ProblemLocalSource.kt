package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ProblemDataSource
import com.nstu.technician.data.datasource.local.dao.ProblemDao
import com.nstu.technician.data.dto.ProblemDTO
import com.nstu.technician.data.until.toProblem
import com.nstu.technician.data.until.toProblemDTO
import com.nstu.technician.data.until.toProblemEntity
import com.nstu.technician.domain.exceptions.NotFoundException
import javax.inject.Inject

class ProblemLocalSource @Inject constructor(
    private val problemDao: ProblemDao
) : ProblemDataSource {

    companion object {
        const val TAG = "ProblemLocalSource"
    }

    override suspend fun delete(obj: ProblemDTO) {
        problemDao.delete(obj.toProblemEntity())
    }

    override suspend fun findById(id: Long): ProblemDTO {
        return problemDao.findById(id)?.toProblemDTO() ?: throw NotFoundException(TAG, "problem by id=$id not found")
    }

    override suspend fun save(obj: ProblemDTO): Long {
        return problemDao.save(obj.toProblemEntity())
    }
}