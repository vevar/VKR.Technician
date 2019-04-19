package com.nstu.technician.data.datasource.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nstu.technician.data.TestAppDataBase
import com.nstu.technician.data.datasource.ShiftDataSource
import com.nstu.technician.data.di.component.DaggerDataSourceComponent
import com.nstu.technician.data.di.model.DaoModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.dto.common.AddressDTO
import com.nstu.technician.data.dto.common.GPSPointDTO
import com.nstu.technician.data.dto.job.FacilityDTO
import com.nstu.technician.data.dto.job.ShiftDTO
import com.nstu.technician.domain.model.common.OwnDateTime
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShiftLocalSourceTest {

    private lateinit var shiftLocalSource: ShiftDataSource
    private lateinit var testAppDataBase: TestAppDataBase

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        testAppDataBase = Room.inMemoryDatabaseBuilder(context, TestAppDataBase::class.java)
            .build()

        val dataSourceComponent = DaggerDataSourceComponent.builder()
            .daoModule(DaoModule(testAppDataBase))
            .dataSourceModule(DataSourceModule())
            .build()
        shiftLocalSource = dataSourceComponent.shiftDataSource()
    }

    @After
    fun closeDb() {
        testAppDataBase.close()
    }


    @Test
    fun writeAndRead() {
        val addressDTO = AddressDTO(
            street = "Пупкина", office = "11", home = "2",
            location = GPSPointDTO(1, 54.04, 54.05)
        )
        val facilityDTO = FacilityDTO(
            oid = 1, name = "Пушка", address = addressDTO, assingmentDate = OwnDateTime(1554713066603)
        )

        val expectedShiftDTO = ShiftDTO(
            oid = 1,
            date = OwnDateTime(1554713066603)
        )

        runBlocking {
            shiftLocalSource.save(expectedShiftDTO)
        }
        val actualShiftDTO = runBlocking {
            shiftLocalSource.findById(facilityDTO.oid)
        }

        Assert.assertEquals(expectedShiftDTO, actualShiftDTO)
    }
}