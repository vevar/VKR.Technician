package com.nstu.technician.data.datasource.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nstu.technician.data.TestAppDataBase
import com.nstu.technician.data.datasource.entity.ArtifactDataSource
import com.nstu.technician.data.di.component.DaggerDataSourceComponent
import com.nstu.technician.data.di.model.DaoModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.dto.getArtifactDTO
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtifactLocalSourceTest {

    private lateinit var artifactDataSource: ArtifactDataSource
    private lateinit var testAppDataBase: TestAppDataBase

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        testAppDataBase = Room.inMemoryDatabaseBuilder(context, TestAppDataBase::class.java).build()

        val dataSourceComponent = DaggerDataSourceComponent.builder()
            .daoModule(DaoModule(testAppDataBase))
            .dataSourceModule(DataSourceModule())
            .build()
        artifactDataSource = dataSourceComponent.artifactDataSource()
    }

    @Test
    fun writeAndRead() {
        val expectedArtifactDTO = getArtifactDTO(12, 1)

        runBlocking {
            artifactDataSource.save(expectedArtifactDTO)
        }
        val actualArtifact = runBlocking {
            artifactDataSource.findById(expectedArtifactDTO.oid)
        }
        assertEquals(expectedArtifactDTO, actualArtifact)
    }
}