package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.datasource.entity.MaintenanceJobDataSource
import com.nstu.technician.data.di.component.DaggerCloudSourceComponent
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.dto.getMaintenanceJobDTO
import com.nstu.technician.data.dto.getRandomId
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.domain.usecase.JobState
import kotlinx.coroutines.runBlocking
import org.junit.Before

class MaintenanceJobCloudSourceTest {

    private lateinit var maintenanceJobDataSource: MaintenanceJobDataSource

    @Before
    fun init() {
        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())
        maintenanceJobDataSource = DaggerCloudSourceComponent.builder()
            .apiModule(ApiModule(apiProvider))
            .dataSourceModule(DataSourceModule())
            .build().maintenanceJobCloudSource()
    }

    //TODO BAD TEST
    fun saveState() {
        val jobStateSize = JobState.size
        val maintenanceJobDTO = getMaintenanceJobDTO(getRandomId()).let {
            it.copy(jobState = (it.jobState + jobStateSize) % (jobStateSize - 1))
        }
        runBlocking { maintenanceJobDataSource.saveState(maintenanceJobDTO) }
    }
}