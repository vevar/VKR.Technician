package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.datasource.ContractorDataSource
import com.nstu.technician.data.dto.getContractorDTO
import com.nstu.technician.data.util.DataBaseProvider
import com.nstu.technician.data.util.DataSourceComponentBuilder
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class ContractorLocalSourceTest {
    private lateinit var contractorLocalDataSource: ContractorDataSource
    private lateinit var dataBase: AppDataBase

    @Before
    fun init() {
        dataBase = DataBaseProvider().getAppDataBase()

        val dataSourceComponent = DataSourceComponentBuilder()
            .dataBase(dataBase)
            .build()

        contractorLocalDataSource = dataSourceComponent.contractorDataSource()
    }

    @After
    fun closeDb() {
        dataBase.close()
    }


    @Test
    fun writeAndRead() {
        val expected = getContractorDTO(5342)

        runBlocking {
            contractorLocalDataSource.save(expected)
        }
        val actual = runBlocking {
            contractorLocalDataSource.findById(expected.oid)
        }

        Assert.assertEquals(expected, actual)
    }
}