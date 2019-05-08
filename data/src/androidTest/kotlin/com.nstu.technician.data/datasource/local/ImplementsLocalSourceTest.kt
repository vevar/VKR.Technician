package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.datasource.entity.ImplementsDataSource
import com.nstu.technician.data.dto.*
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.util.DataBaseProvider
import com.nstu.technician.data.util.DataSourceComponentBuilder
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
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
    fun writeAndFindByMaintenanceJob() {
        val maintenanceJobDTO = getMaintenanceJobDTO(1124)
        val expected =
            maintenanceJobDTO.implList.map { it.getObject() }

        runBlocking {
            expected.forEach {
                implementsDataSource.saveForMaintenanceJob(it, maintenanceJobDTO.oid)
            }
        }
        val actual = runBlocking {
            implementsDataSource.findByMaintenanceJobId(maintenanceJobDTO.oid)
        }

        assertEquals(expected, actual)
    }

    @Test
    fun writeAndFindByJobType() {
        val jobTypeDTO = getJobTypeDTO(1124)
        val expected = jobTypeDTO.impList.map {
            it.getObject()
        }

        runBlocking {
            expected.forEach {
                implementsDataSource.saveForMaintenanceJob(it, 1)
                implementsDataSource.saveForJobTypeId(it, jobTypeDTO.oid)
            }
        }
        val actual = runBlocking {
            implementsDataSource.findByJobTypeId(jobTypeDTO.oid)
        }

        assertEquals(expected, actual)
    }

    @Test
    fun saveAndFindById_ReturnsImplements() {
        val expected = getImplementsDTO(getRandomId())
        runBlocking { implementsDataSource.save(expected) }
        val actual = runBlocking { implementsDataSource.findById(expected.oid) }
        assertEquals(expected, actual)
    }

    @Test
    fun saveAndFindAll_ReturnsListImplements() {
        val expected = getListSomeObject { getImplementsDTO(getRandomId()) }.sortedBy { it.oid }
        runBlocking { implementsDataSource.saveAll(expected) }
        val actual = runBlocking { implementsDataSource.findAll() }
        assertEquals(expected, actual)
    }

    @Test
    fun deleteFindById_ReturnsNull() {
        val implementsDTO = getImplementsDTO(getRandomId())
        runBlocking { implementsDataSource.save(implementsDTO) }
        runBlocking { implementsDataSource.delete(implementsDTO) }
        val actual = runBlocking { implementsDataSource.findById(implementsDTO.oid) }
        assertEquals(null, actual)
    }

    @Test
    fun deleteAllFindAll_ReturnsEmptyList() {
        val expected = getListSomeObject { getImplementsDTO(getRandomId()) }
        runBlocking { implementsDataSource.saveAll(expected) }
        runBlocking { implementsDataSource.deleteAll() }
        val actual = runBlocking { implementsDataSource.findAll() }
        assertEquals(0, actual.size)
    }
}