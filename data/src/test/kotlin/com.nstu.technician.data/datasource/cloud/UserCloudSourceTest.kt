package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.network.RetrofitProvide
import com.nstu.technician.domain.exceptions.UserNotFound
import com.nstu.technician.domain.model.user.Account
import org.junit.Assert.*
import org.junit.Test

class UserCloudSourceTest {

    private val retrofitProvide: RetrofitProvide = RetrofitProvide()
    private val userCloudSource: UserCloudSource = UserCloudSource(retrofitProvide.createUserApi())

    @Test
    fun findById_CorrectAccount_ReturnsUser() {
        val account = Account(0, "root", "1234")
        val user = userCloudSource.findByAccount(account)
        assertNotEquals(null, user)
    }

    @Test
    fun findById_IncorrectAccount_ReturnsUser() {
        val account = Account(0, "roqot", "12w34")
        try {
            val user = userCloudSource.findByAccount(account)
        }catch (throwable: Throwable){
            assertTrue(throwable is UserNotFound)
        }
    }
}