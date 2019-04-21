package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.datasource.ContractDataSource
import com.nstu.technician.data.dto.getContractDTO
import com.nstu.technician.data.util.DataBaseProvider
import com.nstu.technician.data.util.DataSourceComponentBuilder
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ContractLocalSourceTest {
    private lateinit var contractLocalSource: ContractDataSource
    private lateinit var dataBase: AppDataBase

    @Before
    fun init() {
        dataBase = DataBaseProvider().getAppDataBase()

        val dataSourceComponent = DataSourceComponentBuilder()
            .dataBase(dataBase)
            .build()

        contractLocalSource = dataSourceComponent.contractDataSource()
    }

    @After
    fun closeDb() {
        dataBase.close()
    }


    @Test
    fun writeAndRead() {
        val expectedContractDTO = getContractDTO(329)

        runBlocking {
            contractLocalSource.save(expectedContractDTO)
        }
        val contractDTO = runBlocking {
            contractLocalSource.findById(expectedContractDTO.oid)
        }

        Assert.assertEquals(expectedContractDTO, contractDTO)
    }
}