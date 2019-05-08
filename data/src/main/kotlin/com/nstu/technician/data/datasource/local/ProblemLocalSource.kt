package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ProblemDataSource
import com.nstu.technician.data.dto.ProblemDTO
import javax.inject.Inject

class ProblemLocalSource @Inject constructor(): ProblemDataSource {

    override suspend fun delete(obj: ProblemDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): ProblemDTO? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(obj: ProblemDTO): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}