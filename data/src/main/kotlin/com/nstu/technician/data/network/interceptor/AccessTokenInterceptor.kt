package com.nstu.technician.data.network.interceptor

import com.nstu.technician.data.network.constant.HEADER_SESSION_TOKEN
import com.nstu.technician.data.network.token.AccessTokenProvider
import com.nstu.technician.domain.exceptions.UnauthorizedException
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private var tokenProvider: AccessTokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var token = runBlocking {
            tokenProvider.token()
        }
        return if (token == null) {
            chain.proceed(chain.request())
        } else {
            val authenticatedRequest = chain.request()
                .newBuilder()
                .addHeader(HEADER_SESSION_TOKEN, "$token")
                .build()
            return try {
                chain.proceed(authenticatedRequest)
            }catch (e: UnauthorizedException){
                token = runBlocking { tokenProvider.refreshToken() }
                val repeatRequest = chain.request()
                    .newBuilder()
                    .addHeader(HEADER_SESSION_TOKEN, "$token")
                    .build()

                chain.proceed(repeatRequest)
            }
        }
    }
}