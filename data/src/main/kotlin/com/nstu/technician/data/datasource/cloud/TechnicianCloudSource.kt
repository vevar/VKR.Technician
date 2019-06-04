package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.BODY_MUST_BE_SET
import com.nstu.technician.data.datasource.cloud.api.TechnicianApi
import com.nstu.technician.data.datasource.entity.TechnicianDataSource
import com.nstu.technician.data.dto.user.TechnicianDTO
import com.nstu.technician.data.until.logCodeOfResponse
import javax.inject.Inject

class TechnicianCloudSource @Inject constructor(
    private val technicianApi: TechnicianApi
) : TechnicianDataSource {
    override suspend fun findById(id: Long): TechnicianDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(obj: TechnicianDTO): Long {
        technicianApi.updateTechnician(obj)
    }

    override suspend fun delete(obj: TechnicianDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val TAG = "NETWORK_TECHNICIAN"
    }

    override suspend fun findByUserId(userId: Long): TechnicianDTO {
        val response = technicianApi.getTechnicianById(userId).execute()
        val code = response.code()
        logCodeOfResponse(TAG, code)
        return response.body() ?: throw IllegalStateException(BODY_MUST_BE_SET)
    }


}