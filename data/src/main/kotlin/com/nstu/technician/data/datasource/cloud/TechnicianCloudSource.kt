package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.TechnicianDataSource
import com.nstu.technician.data.datasource.cloud.api.TechnicianApi
import com.nstu.technician.data.network.constant.BAD_REQUEST
import com.nstu.technician.data.network.constant.OK
import com.nstu.technician.data.until.LogCodeOfResponse
import com.nstu.technician.domain.exceptions.BadRequestException
import com.nstu.technician.domain.exceptions.NetworkException
import com.nstu.technician.domain.exceptions.UserNotFoundException
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User
import okhttp3.*
import javax.inject.Inject

class TechnicianCloudSource @Inject constructor(
    private val technicianApi: TechnicianApi
) : TechnicianDataSource {

    companion object {
        const val TAG = "NETWORK_TECHNICIAN"
    }

    override fun findByUser(user: User): Technician {
        val response = technicianApi.getTechnicianById(user.account.oid).execute()
        val code = response.code()
        LogCodeOfResponse(TAG, code)

        OkHttpClient.Builder()


        return when (code) {
            OK -> {
                response.body() ?: throw UserNotFoundException()
            }
            BAD_REQUEST -> {
                throw BadRequestException(response.message())
            }
            else -> {
                throw NetworkException("$code :: ${response.message()}")
            }
        }
    }

    override fun save(technician: Technician) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}