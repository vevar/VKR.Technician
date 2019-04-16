package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.UserDTO
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.domain.exceptions.UserNotFoundException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class UserCloudSourceTest {

    private lateinit var userCloudSource: UserCloudSource

    @Before
    fun init(){
        val retrofit = NetworkClientTest().buildRetrofitProvider()
        val apiProvider = ApiProvider(retrofit)
        userCloudSource = UserCloudSource(apiProvider.createUserApi())
    }

    @Test
    suspend fun findById_CorrectAccount_ReturnsUser() {
        val account = AccountDTO(0, "root", "1234")
        val user = userCloudSource.findByAccount(account)
        assertNotEquals(null, user)
    }

    @Test
    suspend fun findById_IncorrectAccount_ReturnsUser() {
        val account = AccountDTO(0, "roqot", "12w34")
        var user: UserDTO? = null
        try {
            user = userCloudSource.findByAccount(account)
        } catch (throwable: UserNotFoundException){

        }
        finally {
            assertEquals(null, user)

        }
    }
}