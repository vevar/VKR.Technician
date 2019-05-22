package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.entity.ProblemDataSource
import com.nstu.technician.data.until.toProblem
import com.nstu.technician.data.until.toProblemDTO
import com.nstu.technician.domain.model.Problem
import com.nstu.technician.domain.repository.ProblemRepository
import javax.inject.Inject
import javax.inject.Named

class ProblemRepositoryImpl @Inject constructor(
    @Named(CLOUD)
    private val problemCloudSource: ProblemDataSource,
    @Named(LOCAL)
    private val problemLocalSource: ProblemDataSource
) : ProblemRepository {

    override suspend fun save(obj: Problem): Problem? {
        return problemCloudSource.save(obj.toProblemDTO()).let { obj.copy(oid = it) }
    }

    override suspend fun findById(id: Long): Problem {
        return problemCloudSource.findById(id).toProblem()
    }

    override suspend fun delete(obj: Problem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}