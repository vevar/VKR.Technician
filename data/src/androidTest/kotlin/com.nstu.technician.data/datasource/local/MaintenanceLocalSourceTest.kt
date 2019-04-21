package com.nstu.technician.data.datasource.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nstu.technician.data.TestAppDataBase
import com.nstu.technician.data.datasource.MaintenanceDataSource
import com.nstu.technician.data.di.component.DaggerDataSourceComponent
import com.nstu.technician.data.di.model.DaoModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.dto.getMaintenanceDTOWithShiftDTO
import com.nstu.technician.data.dto.getShiftDTO
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MaintenanceLocalSourceTest {

    private lateinit var maintenanceDataSource: MaintenanceDataSource
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
        maintenanceDataSource = dataSourceComponent.maintenanceDataSource()
    }

    @After
    fun closeDb() {
        testAppDataBase.close()
    }


    @Test
    fun writeAndRead() {
        val shiftDTO = getShiftDTO(687)
        val expectedMaintenanceDTO = getMaintenanceDTOWithShiftDTO(478, shiftDTO)

        runBlocking {
            maintenanceDataSource.save(expectedMaintenanceDTO)
        }
        val actual = runBlocking {
            maintenanceDataSource.findById(expectedMaintenanceDTO.oid)
        }
        assertEquals(expectedMaintenanceDTO, actual)
    }
}