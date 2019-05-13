package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.datasource.entity.ContractDataSource
import com.nstu.technician.data.di.component.DaggerCloudSourceComponent
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ContractCloudSourceTest {

    private lateinit var contractCloudSource: ContractDataSource

    @Before
    fun init() {
        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())
        contractCloudSource = DaggerCloudSourceComponent.builder()
            .apiModule(ApiModule(apiProvider))
            .dataSourceModule(DataSourceModule())
            .build().contractCloudSource()
    }

    @Test
    fun findById_2_ReturnsContract() {
        val expected = 2L
        val actual = runBlocking { contractCloudSource.findById(expected) }
        assertEquals(expected, actual.oid)
    }

    @Test
    fun findById_NotExist_ThrowNotFoundException() {
        try {
            runBlocking { contractCloudSource.findById(-3) }
        } catch (e: NotFoundException) {
        }

    }
}