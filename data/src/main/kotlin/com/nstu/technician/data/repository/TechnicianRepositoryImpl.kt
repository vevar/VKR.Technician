package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.TechnicianDataSource
import com.nstu.technician.data.until.convertToModel
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User
import com.nstu.technician.domain.repository.TechnicianRepository
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject
import javax.inject.Named

class TechnicianRepositoryImpl @Inject constructor(
    @Named("Cloud")
    private val cloudTechnicianDataSource: TechnicianDataSource
) : TechnicianRepository {

    override suspend fun findByUser(user: User): Technician?{
        return supervisorScope {
            cloudTechnicianDataSource.findByUserId(user.oid)?.let {
                convertToModel(it)
            }
        }
    }


}