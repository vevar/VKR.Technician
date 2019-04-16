package com.nstu.technician.data.repository

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.di.component.DaggerPlanJobsComponent
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.di.model.RepositoryModule
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.domain.repository.ShiftRepository
import com.nstu.technician.domain.usecase.job.LoadShiftsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class ShiftRepositoryTest {

    lateinit var shiftRepository: ShiftRepository

    @Before
    fun init() {
        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())
        val planJobsComponent = DaggerPlanJobsComponent.builder()
            .apiModule(ApiModule(apiProvider))
            .dataSourceModule(DataSourceModule())
            .repositoryModule(RepositoryModule()).build()

        shiftRepository = planJobsComponent.shiftRepository()
    }

    @Test
    fun findByTechnicianIdAndTimePeriod_correctTechAndToday_ReturnsListShifts() {
        val today: Calendar = Calendar.getInstance()

        val startTime = Calendar.getInstance()
        startTime.timeInMillis = today.timeInMillis
        startTime.add(Calendar.DAY_OF_MONTH, -LoadShiftsUseCase.RANGE_OF_LOADING_SHIFTS)

        val endTime = Calendar.getInstance()
        endTime.timeInMillis = today.timeInMillis
        endTime.add(Calendar.DAY_OF_MONTH, LoadShiftsUseCase.RANGE_OF_LOADING_SHIFTS)
        val shifts = runBlocking {
            shiftRepository.findByTechnicianIdAndTimePeriod(2, startTime.timeInMillis, endTime.timeInMillis)
        }
        assertEquals(5, shifts?.size)
    }

    @Test
    fun findById_correctShift_ReturnsShift(){
        val shift = runBlocking {
            shiftRepository.findById(2)
        }
        assertNotEquals(null, shift)
    }

}