package com.nstu.technician.data.datasource.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.datasource.ImplementUnitDataSource
import com.nstu.technician.data.dto.getImplementUnitDTO
import com.nstu.technician.data.util.DataBaseProvider
import com.nstu.technician.data.util.DataSourceComponentBuilder
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImplementUnitLocalSourceTest {

    private lateinit var implementUnitLocalSource: ImplementUnitDataSource
    private lateinit var dataBase: AppDataBase

    @Before
    fun init() {
        dataBase = DataBaseProvider().getAppDataBase()

        val dataSourceComponent = DataSourceComponentBuilder()
            .dataBase(dataBase)
            .build()

        implementUnitLocalSource = dataSourceComponent.implementUnitDataSource()
    }

    @After
    fun closeDb() {
        dataBase.close()
    }

    @Test
    fun writeAndRead() {
        val expected = getImplementUnitDTO(372)

        runBlocking {
            implementUnitLocalSource.save(expected)
        }
        val actual = runBlocking {
            implementUnitLocalSource
        }

        Assert.assertEquals(expected, actual)
    }
}