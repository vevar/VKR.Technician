package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.network.constant.HEADER_SESSION_TOKEN
import com.nstu.technician.data.network.interceptor.HandlerExceptionsInterceptor
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.data.network.retorfit.RetrofitProvider
import com.nstu.technician.domain.exceptions.NotFoundException
import com.nstu.technician.domain.exceptions.UnauthorizedException
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class TechnicianCloudSourceTest {

    private lateinit var technicianCloudSource: TechnicianCloudSource

    private lateinit var correctUser: User
    private val correctAccount: Account = Account(0, "root", "1234")

    @Before
    fun init() {
        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())

        val userCloudSource = UserCloudSource(apiProvider.createUserApi())
        correctUser = runBlocking {
            userCloudSource.findByAccount(correctAccount)
        }
        technicianCloudSource = TechnicianCloudSource(apiProvider.createTechnicianApi())
    }

    @Test
    fun findById_CorrectUser_ReturnsTechnician() {
        val technician = runBlocking {
            technicianCloudSource.findByUserId(correctUser.oid)
        }
        assertNotEquals(null, technician)
        val user = correctUser.copy()
        user.sessionToken = ""
        val correctTechnician = Technician(2, user)
        assertEquals(correctTechnician, technician)
    }

    @Test
    fun findById_NotTechnician_ReturnsUnauthorizedException() {
        var technician: Technician? = null
        try {
            val user = correctUser.copy(3)
            runBlocking {
                technician = technicianCloudSource.findByUserId(user.oid)
            }
        } catch (throwable: NotFoundException) {

        } finally {
            assertEquals(null, technician)
        }
    }


    @Test
    fun findById_IncorrectToken_ReturnUnauthorizedException() {
        val retrofitProvider = RetrofitProvider()
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val authenticatedRequest = chain.request()
                    .newBuilder()
                    .addHeader(HEADER_SESSION_TOKEN, "asd")
                    .build()
                chain.proceed(authenticatedRequest)
            }
            .addInterceptor(HandlerExceptionsInterceptor())
            .build()
        retrofitProvider.addClient(okHttpClient)

        val apiProvider = ApiProvider(retrofitProvider)
        val technicianCloudSource = TechnicianCloudSource(apiProvider.createTechnicianApi())

        val user = correctUser.copy(3)

        var technician: Technician? = null
        try {
            technician = runBlocking {
                technicianCloudSource.findByUserId(user.oid)
            }
        } catch (throwable: UnauthorizedException) {

        } finally {
            assertEquals(null, technician)
        }
    }


    @Test
    fun findById_TokenEmpty_ReturnUnauthorizedException() {
        val retrofitProvider = RetrofitProvider()
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val authenticatedRequest = chain.request()
                    .newBuilder()
                    .addHeader(HEADER_SESSION_TOKEN, "")
                    .build()
                chain.proceed(authenticatedRequest)
            }
            .addInterceptor(HandlerExceptionsInterceptor())
            .build()
        retrofitProvider.addClient(okHttpClient)

        val apiProvider = ApiProvider(retrofitProvider)
        val technicianCloudSource = TechnicianCloudSource(apiProvider.createTechnicianApi())

        var technician: Technician? = null
        try {
            technician = runBlocking {
                technicianCloudSource.findByUserId(correctUser.oid)
            }
        } catch (throwable: UnauthorizedException) {

        } finally {
            assertEquals(null, technician)
        }
    }
}