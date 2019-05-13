package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.datasource.entity.ComponentDataSource
import com.nstu.technician.data.di.component.DaggerCloudSourceComponent
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class ComponentCloudSourceTest {

    private lateinit var componentCloudSource: ComponentDataSource

    @Before
    fun init() {
        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())
        componentCloudSource = DaggerCloudSourceComponent.builder()
            .apiModule(ApiModule(apiProvider))
            .dataSourceModule(DataSourceModule())
            .build().componentCloudSource()
    }

    @Test
    fun findById_2_ReturnsComponent() {
        val expected = 2L
        val actual = runBlocking { componentCloudSource.findById(expected) }.oid
        assertEquals(expected, actual)
    }

    @Test
    fun findById_0_ThrowNotFoundException() {
        try {
            runBlocking { componentCloudSource.findById(0) }
        } catch (e: NotFoundException) {
        }
    }

    @Test
    fun findAll_ReturnNotEmptyList() {
        val list = runBlocking { componentCloudSource.findAll() }
        assertNotEquals(0, list.size)
    }
}