package com.nstu.technician.data.network.interceptor

import com.nstu.technician.data.network.constant.NOT_FOUND_CODE
import com.nstu.technician.data.network.constant.UNAUTHORIZED_CODE
import com.nstu.technician.domain.exceptions.NetworkException
import com.nstu.technician.domain.exceptions.NotFoundException
import com.nstu.technician.domain.exceptions.UnauthorizedException
import okhttp3.Interceptor
import okhttp3.Response

class HandlerExceptionsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.isSuccessful) {
            return response
        } else {
            when (response.code()) {
                UNAUTHORIZED_CODE -> {
                    throw UnauthorizedException(response.message())
                }
                NOT_FOUND_CODE -> {
                    throw NotFoundException(response.request().url().toString(), "Not found")
                }
                else -> {
                    throw NetworkException(response.message())
                }
            }
        }
    }
}