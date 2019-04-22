package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.datasource.ImplementsDataSource
import com.nstu.technician.data.dto.getMaintenanceJobDTO
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.util.DataBaseProvider
import com.nstu.technician.data.util.DataSourceComponentBuilder
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ImplementsLocalSourceTest {

    private lateinit var implementsDataSource: ImplementsDataSource
    private lateinit var dataBase: AppDataBase

    @Before
    fun init() {
        dataBase = DataBaseProvider().getAppDataBase()

        val dataSourceComponent = DataSourceComponentBuilder()
            .dataBase(dataBase)
            .build()

        implementsDataSource = dataSourceComponent.implementsDataSource()
    }

    @After
    fun closeDb() {
        dataBase.close()
    }

    @Test
    fun writeAndFindByImplements() {
        val maintenanceJobDTO = getMaintenanceJobDTO(1124)
        val expected = maintenanceJobDTO.implList?.map { it.getObject() } ?: throw IllegalStateException("implList must be set")

        runBlocking {
            implementsDataSource.saveAllForMaintenanceJob(expected, maintenanceJobDTO.oid)
        }
        val actual = runBlocking {
            implementsDataSource.findByMaintenanceJobId(maintenanceJobDTO.oid)
        }

        Assert.assertEquals(expected, actual)
    }
}