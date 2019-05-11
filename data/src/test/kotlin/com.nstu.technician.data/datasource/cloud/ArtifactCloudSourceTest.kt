package com.nstu.technician.data.datasource.cloud

import android.graphics.BitmapFactory
import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.datasource.entity.ArtifactDataSource
import com.nstu.technician.data.di.component.DaggerCloudSourceComponent
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.network.retorfit.ApiProvider
import org.junit.Before
import org.junit.Test

class ArtifactCloudSourceTest {

    private lateinit var artifactCloudSource: ArtifactDataSource

    @Before
    fun init(){
        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())
        artifactCloudSource = DaggerCloudSourceComponent.builder()
            .apiModule(ApiModule(apiProvider))
            .dataSourceModule(DataSourceModule())
            .build().artifactCloudSource()
    }

    @Test
    fun saveAndFindById(){
        val bitmap = BitmapFactory.decodeFile("data\\src\\sharedTest\\assets\\test_upload_image.jpg")
    }
}