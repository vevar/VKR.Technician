package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.TechnicianDataSource
import com.nstu.technician.data.datasource.cloud.api.TechnicianApi
import com.nstu.technician.data.dto.user.TechnicianDTO
import com.nstu.technician.data.until.logCodeOfResponse
import com.nstu.technician.domain.exceptions.UserNotFoundException
import javax.inject.Inject

class TechnicianCloudSource @Inject constructor(
    private val technicianApi: TechnicianApi
) : TechnicianDataSource {

    companion object {
        const val TAG = "NETWORK_TECHNICIAN"
    }

    override suspend fun findByUserId(userId: Long): TechnicianDTO {
        val response = technicianApi.getTechnicianById(userId).execute()
        val code = response.code()
        logCodeOfResponse(TAG, code)
        return response.body() ?: throw UserNotFoundException()
    }

    override suspend fun save(technician: TechnicianDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}