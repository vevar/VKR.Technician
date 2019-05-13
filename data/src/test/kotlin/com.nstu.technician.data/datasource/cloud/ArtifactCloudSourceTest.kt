package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.datasource.entity.ArtifactDataSource
import com.nstu.technician.data.di.component.DaggerCloudSourceComponent
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ArtifactCloudSourceTest {

    private lateinit var artifactCloudSource: ArtifactDataSource

    @Before
    fun init() {
        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())
        artifactCloudSource = DaggerCloudSourceComponent.builder()
            .apiModule(ApiModule(apiProvider))
            .dataSourceModule(DataSourceModule())
            .build().artifactCloudSource()
    }

    @Test
    fun findById_2_ReturnsArtifact() {
        val expected = 3L
        val artifactDTO = runBlocking { artifactCloudSource.findById(expected) }
        assertEquals(expected, artifactDTO.oid)
    }

    @Test
    fun findById_NotExist_ThrowNotFoundException() {
        try {
            val expected = -3L
            val artifactDTO = runBlocking { artifactCloudSource.findById(expected) }
            assertEquals(expected, artifactDTO.oid)
        } catch (e: NotFoundException) {

        }
    }
}