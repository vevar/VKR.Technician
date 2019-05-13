package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.datasource.entity.ContractorDataSource
import com.nstu.technician.data.di.component.DaggerCloudSourceComponent
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ContractorCloudSourceTest {

    private lateinit var contractorCloudSource: ContractorDataSource

    @Before
    fun init() {
        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())
        contractorCloudSource = DaggerCloudSourceComponent.builder()
            .apiModule(ApiModule(apiProvider))
            .dataSourceModule(DataSourceModule())
            .build().contractorCloudSource()
    }

    @Test
    fun findById_2_ReturnsContractor() {
        val expected = 2L
        val actual = runBlocking { contractorCloudSource.findById(expected) }
        assertEquals(expected, actual.oid)
    }

    @Test
    fun findById_NotExist_ThrowNotFoundException() {
        try {
            runBlocking { contractorCloudSource.findById(-3) }
        } catch (e: NotFoundException) {
        }

    }
}