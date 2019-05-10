package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.TechnicianDataSource
import com.nstu.technician.data.until.toTechnician
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User
import com.nstu.technician.domain.repository.TechnicianRepository
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject
import javax.inject.Named

class TechnicianRepositoryImpl @Inject constructor(
    @Named(CLOUD)
    private val cloudTechnicianDataSource: TechnicianDataSource
) : TechnicianRepository {
    override suspend fun delete(obj: Technician) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(obj: Technician): Technician? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): Technician {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findByUser(user: User): Technician?{
        return supervisorScope {
            cloudTechnicianDataSource.findByUserId(user.oid).toTechnician()
        }
    }
}