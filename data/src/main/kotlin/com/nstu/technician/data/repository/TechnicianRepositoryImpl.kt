package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.TechnicianDataSource
import com.nstu.technician.domain.model.user.User
import com.nstu.technician.domain.repository.TechnicianRepository
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject
import javax.inject.Named

class TechnicianRepositoryImpl @Inject constructor(
    @Named("Cloud")
    private val cloudTechnicianDataSource: TechnicianDataSource
) : TechnicianRepository {

    override suspend fun findByUser(user: User) = supervisorScope {
        val technician = cloudTechnicianDataSource.findByUserId(user.oid)
//        TODO
//        async {
//            localTechnicianDataSource.save(technician)
//        }
        technician
    }


}