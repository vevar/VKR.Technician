package com.nstu.technician.data.client

import com.nstu.technician.data.di.DaggerAuthComponentTest
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.InterceptorModule
import com.nstu.technician.data.di.model.RepositoryModule
import com.nstu.technician.data.di.module.FakeDaoModule
import com.nstu.technician.data.di.module.OnlyCloudDataSource
import com.nstu.technician.data.network.interceptor.AccessTokenInterceptor
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.data.network.retorfit.RetrofitProvider
import okhttp3.OkHttpClient
import javax.inject.Inject

class ClientTest {

    private var retrofitProvider: RetrofitProvider = RetrofitProvider()

    @Inject
    lateinit var accessTokenInterceptor: AccessTokenInterceptor

    init {
        DaggerAuthComponentTest.builder()
            .apiModule(ApiModule(ApiProvider(retrofitProvider)))
            .fakeDaoModule(FakeDaoModule())
            .interceptorModule(InterceptorModule())
            .onlyCloudDataSource(OnlyCloudDataSource())
            .repositoryModule(RepositoryModule())
            .build().inject(this)
    }

    fun buildRetrofitProvider(): RetrofitProvider {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(accessTokenInterceptor)
            .build()
        retrofitProvider.addClient(okHttpClient)
        return retrofitProvider

    }

}