package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UserCloudSourceTest {

    private lateinit var userCloudSource: UserCloudSource

    @Before
    fun init() {
        val retrofit = NetworkClientTest().buildRetrofitProvider()
        val apiProvider = ApiProvider(retrofit)
        userCloudSource = UserCloudSource(apiProvider.createUserApi())
    }

    @Test
    fun findById_CorrectAccount_ReturnsUser() {
        val account = AccountDTO(0, "root", "1234")
        val user = runBlocking { userCloudSource.findByAccount(account) }
    }

    @Test
    fun findById_IncorrectAccount_ReturnsUser() {
        val account = AccountDTO(0, "roqot", "12w34")
        try {
            runBlocking { userCloudSource.findByAccount(account) }
        } catch (e: NotFoundException){}
    }
}