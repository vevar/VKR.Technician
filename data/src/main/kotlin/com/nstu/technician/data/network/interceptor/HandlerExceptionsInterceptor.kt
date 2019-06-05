package com.nstu.technician.data.network.interceptor

import android.util.Log
import com.nstu.technician.data.network.constant.NOT_FOUND_CODE
import com.nstu.technician.data.network.constant.UNAUTHORIZED_CODE
import com.nstu.technician.domain.exceptions.NetworkException
import com.nstu.technician.domain.exceptions.NotFoundException
import com.nstu.technician.domain.exceptions.UnauthorizedException
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

class HandlerExceptionsInterceptor : Interceptor {

    companion object {
        const val MAX_REPEAT = 5
        const val TAG = "ExceptionsInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val timeoutMillis = chain.connectTimeoutMillis()
        return try {
            proceed(chain, timeoutMillis)
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "$timeoutMillis")
            repeatRequest(chain, timeoutMillis, 0)
        }

    }

    private fun repeatRequest(chain: Interceptor.Chain, timeoutMillis: Int, counterRequest: Int): Response {
        val counter = counterRequest + 1
        return try {
            proceed(chain, timeoutMillis * counter)
        } catch (e: SocketTimeoutException) {
            if (counter < MAX_REPEAT) {
                Log.d(TAG, "${timeoutMillis * counter}")
                repeatRequest(chain, timeoutMillis, counter)
            } else {
                throw e
            }
        }
    }

    private fun proceed(chain: Interceptor.Chain, timeoutMillis: Int): Response {
        val newChain = chain.
            withConnectTimeout(timeoutMillis, TimeUnit.MILLISECONDS)
            .withWriteTimeout(timeoutMillis, TimeUnit.MILLISECONDS)
            .withReadTimeout(timeoutMillis, TimeUnit.MILLISECONDS)
        val response = newChain.proceed(newChain.request())
        if (response.isSuccessful) {
            return response
        } else {
            when (response.code()) {
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