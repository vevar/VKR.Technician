package com.nstu.technician.data.datasource.cloud

import android.content.ContentResolver
import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.datasource.entity.FileDataSource
import com.nstu.technician.data.di.component.DaggerCloudSourceComponent
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.network.retorfit.ApiProvider
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.File

class FileCloudSourceTest {

    private lateinit var fileCloudSource: FileDataSource
    private lateinit var contentResolver: ContentResolver


    @Before
    fun init() {
        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())
        fileCloudSource = DaggerCloudSourceComponent.builder()
            .apiModule(ApiModule(apiProvider))
            .dataSourceModule(DataSourceModule())
            .build().fileCloudSource()
    }

    @Test
    fun save_Image_ReturnsArtifact() {
        val file = File("C:\\Projects\\Mobile\\VKR.Technician\\data\\src\\sharedTest\\assets\\test_upload_image.jpg")
        val artifactDTO = runBlocking { fileCloudSource.save(file, "image/jpg") }
        val rec = 5
    }
}