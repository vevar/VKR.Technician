package com.nstu.technician.data.network.retorfit

import com.nstu.technician.data.di.component.DaggerAuthComponent
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.di.model.RepositoryModule
import com.nstu.technician.data.network.interceptor.AccessTokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

class Client {

    @Inject
    lateinit var accessTokenInterceptor: AccessTokenInterceptor


    fun buildRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(accessTokenInterceptor)
            .build()

        val retrofit = RetrofitBuilder.builder()
            .client(okHttpClient)
            .build()

        DaggerAuthComponent.builder()
            .apiModule(ApiModule(ApiProvider(retrofit)))
            .dataSourceModule(DataSourceModule())
            .repositoryModule(RepositoryModule())
            .build().inject(this)


        return retrofit
    }
}