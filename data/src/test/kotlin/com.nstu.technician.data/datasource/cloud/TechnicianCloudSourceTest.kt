package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.network.RetrofitProvide
import com.nstu.technician.domain.model.EntityLink
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
    private val correctAccount: Account = Account(0, "root", "1234")
    @Before
    fun init() {
        val userCloudSource = UserCloudSource(retrofitProvide.createUserApi())
        correctUser = userCloudSource.findByAccount(correctAccount)
    }

    @Test
    fun findById_CorrectUser_ReturnsTechnician() {
        val technician = technicianCloudSource.findByUser(correctUser)
        assertNotEquals(null, technician)
    }

    @Test
    fun findById_NotTechnician_ReturnsException() {
        val user = User(1, correctUser.sessionToken, EntityLink(1, correctAccount))
        technicianCloudSource.findByUser(user)
    }

    @Test
    fun findById_IncorrectToken_ReturnTokenIncorrect(){
        val user = User(1, "123", EntityLink(1, correctAccount))
        technicianCloudSource.findByUser(user)
    }

}