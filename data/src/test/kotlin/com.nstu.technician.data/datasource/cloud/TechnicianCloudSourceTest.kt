package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.network.RetrofitProvide
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.User
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class TechnicianCloudSourceTest {

    private val retrofitProvide: RetrofitProvide = RetrofitProvide()
    private val technicianCloudSource: TechnicianCloudSource =
        TechnicianCloudSource(retrofitProvide.createTechnicianApi())
    private lateinit var correctUser: User
    @Before
    fun init() {
        val userCloudSource = UserCloudSource(retrofitProvide.createUserApi())
        correctUser = userCloudSource.findByAccount(Account(0, "root", "1234"))
    }

    @Test
    fun findById_CorrectUser_ReturnsTechnician() {
        val technician = technicianCloudSource.findByUser(correctUser)
        assertNotEquals(null, technician)
    }
}