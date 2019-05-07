package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.TechnicianDataSource
import com.nstu.technician.data.datasource.cloud.api.TechnicianApi
import com.nstu.technician.data.until.logCodeOfResponse
import com.nstu.technician.domain.exceptions.UserNotFoundException
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User
import javax.inject.Inject

class TechnicianCloudSource @Inject constructor(
    private val technicianApi: TechnicianApi
) : TechnicianDataSource {

    companion object {
        const val TAG = "NETWORK_TECHNICIAN"
    }

    override fun findByUser(user: User): Technician {
        val response = technicianApi.getTechnicianById(user.oid).execute()
        val code = response.code()
        logCodeOfResponse(TAG, code)
        return  response.body() ?: throw UserNotFoundException()
    }

    override fun save(technician: Technician) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}