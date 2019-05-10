package com.nstu.technician.data.datasource.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nstu.technician.data.TestAppDataBase
import com.nstu.technician.data.datasource.entity.ShiftDataSource
import com.nstu.technician.data.di.component.DaggerLocalSourceComponent
import com.nstu.technician.data.di.model.DaoModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.dto.getShiftDTO
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShiftLocalSourceTest {

    private lateinit var shiftLocalSource: ShiftDataSource
    private lateinit var testAppDataBase: TestAppDataBase

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        testAppDataBase = Room.inMemoryDatabaseBuilder(context, TestAppDataBase::class.java)
            .build()

        val dataSourceComponent = DaggerLocalSourceComponent.builder()
            .daoModule(DaoModule(testAppDataBase))
            .dataSourceModule(DataSourceModule())
            .build()
        shiftLocalSource = dataSourceComponent.shiftDataSource()
    }

    @After
    fun closeDb() {
        testAppDataBase.close()
    }


    @Test
    fun writeAndRead() {
        val expectedShiftDTO = getShiftDTO(12)

        runBlocking {
            shiftLocalSource.save(expectedShiftDTO)
        }
        val actualShiftDTO = runBlocking {
            shiftLocalSource.findById(expectedShiftDTO.oid)
        }

        Assert.assertEquals(expectedShiftDTO, actualShiftDTO)
    }
}