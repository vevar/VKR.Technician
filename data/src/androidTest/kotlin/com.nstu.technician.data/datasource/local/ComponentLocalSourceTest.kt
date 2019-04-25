package com.nstu.technician.data.datasource.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.datasource.ComponentDataSource
import com.nstu.technician.data.dto.getComponentDTO
import com.nstu.technician.data.dto.getListSomeObject
import com.nstu.technician.data.dto.tool.ComponentDTO
import com.nstu.technician.data.util.DataBaseProvider
import com.nstu.technician.data.util.DataSourceComponentBuilder
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
    fun saveAndFindById() {
        val expected = getComponentDTO(789)

        runBlocking {
            componentLocalSource.save(expected)
        }
        val actual = runBlocking {
            componentLocalSource.findById(expected.oid)
        }
        assertEquals(expected, actual)
    }

    @Test
    fun saveAllAndFindById() {
        val expected = getListSomeObject { getComponentDTO(789) }

        runBlocking {
            componentLocalSource.saveAll(expected)
        }
        val actual = mutableListOf<ComponentDTO>()
        runBlocking {
            expected.forEach { componentDTO ->
                componentLocalSource.findById(componentDTO.oid)?.let {
                    actual.add(it)
                }
            }
        }
        assertEquals(expected, actual)
    }

}