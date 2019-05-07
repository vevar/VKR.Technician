package com.nstu.technician.data.network.interceptor

import com.nstu.technician.data.network.constant.NOT_FOUND
import com.nstu.technician.data.network.constant.OK
import com.nstu.technician.data.network.constant.UNAUTHORIZED
import com.nstu.technician.domain.exceptions.NetworkException
import com.nstu.technician.domain.exceptions.NotFoundException
import com.nstu.technician.domain.exceptions.UnauthorizedException
import okhttp3.Interceptor
import okhttp3.Response

class HandlerExceptionsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val code = response.code()
        if (code in OK..300) {
            return response
        } else {
            when (code) {
                UNAUTHORIZED -> {
                    throw UnauthorizedException(response.message())
                }
                NOT_FOUND -> {
                    throw NotFoundException(response.message())
                }
                else -> {
                    throw NetworkException(response.message())
                }
            }
        }
    }
}