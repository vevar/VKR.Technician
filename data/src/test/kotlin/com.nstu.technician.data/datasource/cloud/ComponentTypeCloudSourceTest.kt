package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.network.retorfit.ApiProvider
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class ComponentTypeCloudSourceTest {

    private lateinit var componentTypeCloudSource: ComponentTypeCloudSource

    @Before
    fun init() {
        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())
        componentTypeCloudSource = ComponentTypeCloudSource(apiProvider.createComponentTypeApi())
    }

    @Test
    fun findById_2_ReturnsComponentTypeDTO() {
        val expected = 2L
        val actual = runBlocking { componentTypeCloudSource.findById(expected) }
        assertEquals(expected, actual?.oid)
    }

    @Test
    fun findAll_ReturnsNotEmptyList() {
        val actual = runBlocking { componentTypeCloudSource.findAll() }
        assertNotEquals(0, actual.size)
    }
}
