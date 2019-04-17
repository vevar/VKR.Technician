package com.nstu.technician.data.repository

import android.test.MoreAsserts.assertNotEqual
import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.di.component.DaggerMapComponent
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.di.model.RepositoryModule
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.domain.repository.FacilityRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FacilityRepositoryTest {

    private lateinit var facilityRepository: FacilityRepository

    @Before
    fun init() {
        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())
        val mapComponent = DaggerMapComponent.builder()
            .apiModule(ApiModule(apiProvider))
            .dataSourceModule(DataSourceModule())
            .repositoryModule(RepositoryModule())
            .build()
        facilityRepository = mapComponent.facilityRepository()
    }

    @Test
    fun findById_correctId_ReturnsFacility() {
        val facility = runBlocking {
            facilityRepository.findById(8)
        }

        assertNotEqual(null, facility)
    }
}