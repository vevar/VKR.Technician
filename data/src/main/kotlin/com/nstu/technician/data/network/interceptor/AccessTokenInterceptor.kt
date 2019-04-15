package com.nstu.technician.data.network.interceptor

import com.nstu.technician.data.network.token.AccessTokenProvider
import com.nstu.technician.data.network.constant.HEADER_SESSION_TOKEN
import com.nstu.technician.data.network.constant.UNAUTHORIZED
import com.nstu.technician.domain.exceptions.UnauthorizedException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private var tokenProvider: AccessTokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.token()
        return if (token == null) {
            chain.proceed(chain.request())
        } else {
            val authenticatedRequest = chain.request()
                .newBuilder()
                .addHeader(HEADER_SESSION_TOKEN, "$token")
                .build()
            return chain.proceed(authenticatedRequest)
        }
    }
}