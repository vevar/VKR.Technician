package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.datasource.ComponentTypeDataSource
import com.nstu.technician.data.dto.getComponentTypeDTO
import com.nstu.technician.data.util.DataBaseProvider
import com.nstu.technician.data.util.DataSourceComponentBuilder
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ComponentTypeLocalSourceTest {

    private lateinit var componentTypeLocalSource: ComponentTypeDataSource
    private lateinit var dataBase: AppDataBase

    @Before
    fun init() {
        dataBase = DataBaseProvider().getAppDataBase()

        val dataSourceComponent = DataSourceComponentBuilder()
            .dataBase(dataBase)
            .build()

        componentTypeLocalSource = dataSourceComponent.componentTypeLocalSource()
    }

    @After
    fun closeDb() {
        dataBase.close()
    }

    @Test
    fun writeAndFindById() {
        val expected = getComponentTypeDTO(47)
        runBlocking {
            componentTypeLocalSource.save(expected)
        }

        val actual = runBlocking {
            componentTypeLocalSource.findById(expected.oid)
        }

        assertEquals(expected, actual)
    }

}