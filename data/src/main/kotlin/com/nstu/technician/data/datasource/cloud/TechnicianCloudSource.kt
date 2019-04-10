package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.TechnicianDataSource
import com.nstu.technician.data.datasource.cloud.api.TechnicianApi
import com.nstu.technician.domain.exceptions.UserNotFound
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User
import javax.inject.Inject

class TechnicianCloudSource @Inject constructor(
    private val technicianApi: TechnicianApi
) : TechnicianDataSource {

    override fun findByUser(user: User): Technician {
        val response = technicianApi.getTechnicianById(user.sessionToken, user.account.oid).execute()
        return response.body() ?: throw UserNotFound("Technician not found")
    }

    override fun save(technician: Technician) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}