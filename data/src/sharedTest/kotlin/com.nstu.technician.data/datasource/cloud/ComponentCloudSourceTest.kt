package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.datasource.entity.ComponentDataSource
import com.nstu.technician.data.network.retorfit.ApiProvider
import org.junit.Before

class ComponentCloudSourceTest {

    private lateinit var componentCloudSource: ComponentDataSource

    @Before
    fun init() {
        val apiProvider = ApiProvider(NetworkClientTest().buildRetrofitProvider())

    }
}