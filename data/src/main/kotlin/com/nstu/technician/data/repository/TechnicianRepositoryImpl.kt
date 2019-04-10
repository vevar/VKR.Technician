package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.TechnicianDataSource
import com.nstu.technician.domain.model.user.User
import com.nstu.technician.domain.repository.TechnicianRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject
import javax.inject.Named

class TechnicianRepositoryImpl @Inject constructor(
    @Named("Local")
    private val localTechnicianDataSource: TechnicianDataSource,
    @Named("Cloud")
    private val cloudTechnicianDataSource: TechnicianDataSource
) : TechnicianRepository {

    override suspend fun findByUser(user: User) = supervisorScope {
        val technician = cloudTechnicianDataSource.findByUser(user)
        async {
            localTechnicianDataSource.save(technician)
        }
        technician
    }


}