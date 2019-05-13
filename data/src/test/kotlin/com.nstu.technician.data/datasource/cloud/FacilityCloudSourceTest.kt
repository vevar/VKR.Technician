package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.datasource.entity.FacilityDataSource
import com.nstu.technician.data.di.component.DaggerCloudSourceComponent
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FacilityCloudSourceTest {

    private lateinit var facilityCloudSource: FacilityDataSource

    @Before
    fun init() {
        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())
        facilityCloudSource = DaggerCloudSourceComponent.builder()
            .apiModule(ApiModule(apiProvider))
            .dataSourceModule(DataSourceModule())
            .build().facilityCloudSource()
    }

    @Test
    fun findById_2_ReturnsFacility() {
        val expected = 2L
        val actual = runBlocking { facilityCloudSource.findById(expected) }
        assertEquals(expected, actual.oid)
    }

    @Test
    fun findById_NotExist_ThrowNotFoundException() {
        try {
            runBlocking { facilityCloudSource.findById(-3) }
        } catch (e: NotFoundException) {
        }

    }
}