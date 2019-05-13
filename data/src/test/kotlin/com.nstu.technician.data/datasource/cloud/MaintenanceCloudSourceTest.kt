package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.datasource.entity.MaintenanceDataSource
import com.nstu.technician.data.di.component.DaggerCloudSourceComponent
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MaintenanceCloudSourceTest {

    private lateinit var maintenanceCloudSource: MaintenanceDataSource

    @Before
    fun init() {
        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())
        maintenanceCloudSource = DaggerCloudSourceComponent.builder()
            .apiModule(ApiModule(apiProvider))
            .dataSourceModule(DataSourceModule())
            .build().maintenanceCloudSource()
    }

    @Test
    fun findById_17_ReturnsMaintenance() {
        val expected = 17L
        val actual = runBlocking { maintenanceCloudSource.findById(expected) }
        assertEquals(expected, actual.oid)
    }

    @Test
    fun findById_NotExist_ThrowNotFoundException() {
        try {
            runBlocking { maintenanceCloudSource.findById(-3) }
        } catch (e: NotFoundException) {
        }

    }
}