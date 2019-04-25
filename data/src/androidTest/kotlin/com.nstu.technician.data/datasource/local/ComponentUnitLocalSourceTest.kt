package com.nstu.technician.data.datasource.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.datasource.ComponentUnitDataSource
import com.nstu.technician.data.dto.getMaintenanceJobDTO
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.util.DataBaseProvider
import com.nstu.technician.data.util.DataSourceComponentBuilder
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComponentUnitLocalSourceTest {

    private lateinit var componentUnitLocalSource: ComponentUnitDataSource
    private lateinit var dataBase: AppDataBase

    @Before
    fun init() {
        dataBase = DataBaseProvider().getAppDataBase()

        val dataSourceComponent = DataSourceComponentBuilder()
            .dataBase(dataBase)
            .build()

        componentUnitLocalSource = dataSourceComponent.componentUnitLocalSource()
    }

    @After
    fun closeDb() {
        dataBase.close()
    }

    @Test
    fun writeAndFindByMaintenanceJobId() {
        val maintenanceJobDTO = getMaintenanceJobDTO(1921)
        val expected = (maintenanceJobDTO.components?.map { it.getObject() }
            ?: throw NullPointerException())
        runBlocking {
            componentUnitLocalSource.saveAllForMaintenanceJob(expected, maintenanceJobDTO)
        }
        val actual = runBlocking {
            componentUnitLocalSource.findByMaintenanceJob(maintenanceJobDTO.oid)
        }
        assertTrue(actual.containsAll(expected))
    }
}