package com.nstu.technician.data.datasource.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.datasource.entity.ComponentDataSource
import com.nstu.technician.data.dto.getComponentDTO
import com.nstu.technician.data.dto.getListSomeObject
import com.nstu.technician.data.dto.getRandomId
import com.nstu.technician.data.dto.tool.ComponentDTO
import com.nstu.technician.data.util.DataBaseProvider
import com.nstu.technician.data.util.DataSourceComponentBuilder
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComponentLocalSourceTest {

    private lateinit var componentLocalSource: ComponentDataSource
    private lateinit var dataBase: AppDataBase

    @Before
    fun init() {
        dataBase = DataBaseProvider().getAppDataBase()

        val dataSourceComponent = DataSourceComponentBuilder()
            .dataBase(dataBase)
            .build()

        componentLocalSource = dataSourceComponent.componentLocalSource()
    }

    @After
    fun closeDb() {
        dataBase.close()
    }

    @Test
    fun saveAndFindById_ReturnsComponent() {
        val expected = getComponentDTO(getRandomId())

        runBlocking {
            componentLocalSource.save(expected)
        }
        val actual = runBlocking {
            componentLocalSource.findById(expected.oid)
        }
        assertEquals(expected, actual)
    }

    @Test
    fun saveAllAndFindById_ReturnsListComponents() {
        val expected = getListSomeObject { getComponentDTO(it) }

        runBlocking {
            componentLocalSource.saveAll(expected)
        }
        val actual = mutableListOf<ComponentDTO>()
        runBlocking {
            expected.forEach { componentDTO ->
                componentLocalSource.findById(componentDTO.oid).let {
                    actual.add(it)
                }
            }
        }
        assertEquals(expected, actual)
    }

    @Test
    fun saveAllAndFindAll_ReturnsNotEmptyList() {
        val expected = getListSomeObject { getComponentDTO(getRandomId()) }
        runBlocking { componentLocalSource.saveAll(expected) }
        val actual = runBlocking { componentLocalSource.findAll() }
        assertEquals(expected.sortedBy { it.oid }, actual)
    }

    @Test
    fun delete_ThrowNotFoundException() {
        val saved = getComponentDTO(getRandomId())
        runBlocking { componentLocalSource.save(saved) }
        runBlocking { componentLocalSource.delete(saved) }
        try {
            runBlocking { componentLocalSource.findById(saved.oid) }
        } catch (e: NotFoundException) {
        }
    }

    @Test
    fun deleteAll_ReturnsEmptyList() {
        val savedList = getListSomeObject { getComponentDTO(getRandomId()) }
        runBlocking { componentLocalSource.saveAll(savedList) }
        runBlocking { componentLocalSource.deleteAll() }
        val actual = runBlocking { componentLocalSource.findAll() }
        assertEquals(0, actual.size)
    }

}