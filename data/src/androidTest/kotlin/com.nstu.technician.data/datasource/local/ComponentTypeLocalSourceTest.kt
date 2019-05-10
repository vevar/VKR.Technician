package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.datasource.entity.ComponentTypeDataSource
import com.nstu.technician.data.dto.getComponentTypeDTO
import com.nstu.technician.data.dto.getListSomeObject
import com.nstu.technician.data.dto.getRandomId
import com.nstu.technician.data.util.DataBaseProvider
import com.nstu.technician.data.util.DataSourceComponentBuilder
import com.nstu.technician.domain.exceptions.NotFoundException
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
        val expected = getComponentTypeDTO(getRandomId())
        runBlocking {
            componentTypeLocalSource.save(expected)
        }

        val actual = runBlocking {
            componentTypeLocalSource.findById(expected.oid)
        }

        assertEquals(expected, actual)
    }

    @Test
    fun delete_obj_throwNotFoundException() {
        val componentTypeId = getRandomId()
        runBlocking { componentTypeLocalSource.save(getComponentTypeDTO(componentTypeId)) }
        val componentTypeDTO = runBlocking { componentTypeLocalSource.findById(componentTypeId) }
        runBlocking { componentTypeLocalSource.delete(componentTypeDTO) }
        try {
            runBlocking { componentTypeLocalSource.findById(componentTypeId) }
        } catch (exception: NotFoundException) {
        }
    }

    @Test
    fun deleteAll_ReturnsEmptyList() {
        runBlocking { componentTypeLocalSource.saveAll(getListSomeObject { getComponentTypeDTO(it) }) }
        runBlocking { componentTypeLocalSource.deleteAll() }
        val actual = runBlocking { componentTypeLocalSource.findAll() }
        assertEquals(0, actual.size)
    }
}