package com.nstu.technician.data.datasource.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.datasource.MaintenanceJobDataSource
import com.nstu.technician.data.dto.getMaintenanceDTO
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.util.DataBaseProvider
import com.nstu.technician.data.util.DataSourceComponentBuilder
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class MaintenanceJobLocalSourceTest {

    private lateinit var maintenanceJobLocalSource: MaintenanceJobDataSource
    private lateinit var dataBase: AppDataBase

    @Before
    fun init() {
        dataBase = DataBaseProvider().getAppDataBase()

        val dataSourceComponent = DataSourceComponentBuilder()
            .dataBase(dataBase)
            .build()

        maintenanceJobLocalSource = dataSourceComponent.maintenanceJobDataSource()
    }

    @After
    fun closeDb() {
        dataBase.close()
    }

    @Test
    fun writeAndFindById() {
        val maintenanceDTO = getMaintenanceDTO(124)
        val expected =
            maintenanceDTO.jobList?.get(0)?.getObject() ?: throw IllegalStateException("implList must be set")

        runBlocking {
            maintenanceJobLocalSource.saveForMaintenance(expected, maintenanceDTO.oid)
        }
        val actual = runBlocking {
            maintenanceJobLocalSource.findById(expected.oid)
        }

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun writeAndFindByMaintenanceId() {
        val maintenanceDTO = getMaintenanceDTO(124)
        val expected =
            maintenanceDTO.jobList?.map { it.getObject() } ?: throw IllegalStateException("implList must be set")

        runBlocking {
            maintenanceJobLocalSource.saveAllForMaintenance(expected, maintenanceDTO.oid)
        }
        val actual = runBlocking {
            maintenanceJobLocalSource.findByMaintenanceId(maintenanceDTO.oid)
        }

        Assert.assertEquals(expected, actual)
    }
}