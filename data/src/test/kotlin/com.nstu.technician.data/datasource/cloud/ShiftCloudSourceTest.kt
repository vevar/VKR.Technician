package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.network.retorfit.ApiProvider
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.*

class ShiftCloudSourceTest {

    private lateinit var shiftCloudSource: ShiftCloudSource
    private val today: Calendar = Calendar.getInstance()

    @Before
    fun init() {

        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())
        shiftCloudSource = ShiftCloudSource(apiProvider.createShiftApi())
    }

    @Test
    fun findByTechnicianIdAndTimePeriod_correctUser_ReturnsListShifts() {
        val startTime = Calendar.getInstance()
        startTime.timeInMillis = today.timeInMillis
        startTime.add(Calendar.DAY_OF_MONTH, -7)

        val endTime = Calendar.getInstance()
        endTime.timeInMillis = today.timeInMillis
        endTime.add(Calendar.DAY_OF_MONTH, 7)

        val shifts = runBlocking {
            shiftCloudSource.findByTechnicianIdAndTimePeriod(2, startTime.timeInMillis, endTime.timeInMillis)
        }
        assertEquals(5, shifts.size)
    }

    @Test
    fun findById_correctUser_ReturnShift() {
        val startTime = Calendar.getInstance()
        startTime.timeInMillis = today.timeInMillis
        startTime.add(Calendar.DAY_OF_MONTH, -7)

        val endTime = Calendar.getInstance()
        endTime.timeInMillis = today.timeInMillis
        endTime.add(Calendar.DAY_OF_MONTH, 7)

        val shift = runBlocking {
            shiftCloudSource.findById(2)
        }
        assertTrue(shift != null)
    }
}
