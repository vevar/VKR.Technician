package com.nstu.technician.data.datasource.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.nstu.technician.data.TestAppDataBase
import com.nstu.technician.data.datasource.AddressDataSource
import com.nstu.technician.data.di.component.DaggerDataSourceComponent
import com.nstu.technician.data.di.model.DaoModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.dto.common.AddressDTO
import com.nstu.technician.data.dto.common.GPSPointDTO
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AddressLocalSourceTest {

    private lateinit var addressLocalSource: AddressDataSource
    private lateinit var testAppDataBase: TestAppDataBase

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        testAppDataBase = Room.inMemoryDatabaseBuilder(context, TestAppDataBase::class.java)
            .build()

        val dataSourceComponent = DaggerDataSourceComponent.builder()
            .daoModule(DaoModule(testAppDataBase))
            .dataSourceModule(DataSourceModule())
            .build()
        addressLocalSource = dataSourceComponent.addressDataSource()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        testAppDataBase.close()
    }


    @Test
    @Throws(IOException::class)
    @SmallTest
    fun writeAndReadAddress() {
        val addressDTO = AddressDTO(
            street = "Пупкина", office = "11", home = "2",
            location = GPSPointDTO(1, 54.04, 54.05)
        )
        addressLocalSource.save(addressDTO)
        val actualAddress = addressLocalSource.findById(addressDTO.location.oid)
        assertEquals(addressDTO, actualAddress)
    }

}