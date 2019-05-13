package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class ImplementsCloudSourceTest {

    private lateinit var implementsCloudSource: ImplementsCloudSource

    @Before
    fun init() {
        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())
        implementsCloudSource = ImplementsCloudSource(apiProvider.createImplementsApi())
    }

    @Test
    fun findById_2_ReturnsImplements() {
        val actual = runBlocking { implementsCloudSource.findById(2) }
        assertNotEquals(null, actual)
    }

    @Test
    fun findById_NotExist_ThrowNotFoundException() {
        try {
            runBlocking { implementsCloudSource.findById(-3) }
        } catch (e: NotFoundException) {
        }
    }

    @Test
    fun findAll_ReturnsListImplements() {
        val actual = runBlocking { implementsCloudSource.findAll() }
        assertNotEquals(0, actual.size)
    }
}